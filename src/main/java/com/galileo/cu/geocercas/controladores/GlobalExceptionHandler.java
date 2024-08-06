package com.galileo.cu.geocercas.controladores;

import com.galileo.cu.geocercas.entidades.ErrorDetail;
import com.galileo.cu.geocercas.exceptions.GeocercaLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details;
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField()+ " : " +error.getDefaultMessage())
                .collect(Collectors.toList());
        return internalHandler(ex, status, details, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

        return internalHandler(ex, status, details, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<>();


        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        return internalHandler(ex, status, details, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        return internalHandler(ex, status, details, request);
    }

    @ExceptionHandler({ GeocercaLimitException.class })
    public ResponseEntity<Object> handleGeocercaLimitException(GeocercaLimitException ex, WebRequest request) {
        log.error("ERROR", ex);
        return handleExceptionInternal(
                ex,
                buildError(HttpStatus.PRECONDITION_FAILED, Collections.singletonList(ex.getLocalizedMessage())),
                new HttpHeaders(),
                HttpStatus.PRECONDITION_FAILED,
                request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        log.error("ERROR", ex);
        return handleExceptionInternal(
                ex,
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getLocalizedMessage())),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    private ResponseEntity<Object> internalHandler(Exception ex, HttpStatus status, WebRequest request){
        return handleExceptionInternal(
                ex,
                buildError(status, Collections.singletonList(ex.getLocalizedMessage())),
                new HttpHeaders(),
                status,
                request);
    }

    private ResponseEntity<Object> internalHandler(Exception ex, HttpStatus status, List<String> details,  WebRequest request){
        log.error("ERROR", ex);
        return handleExceptionInternal(
                ex,
                buildError(status, details),
                new HttpHeaders(),
                status,
                request);
    }


     private static ErrorDetail buildError(HttpStatus status, List<String> errors){
        return new ErrorDetail(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                errors
        );
     }
}
