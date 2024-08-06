package com.galileo.cu.geocercas.controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galileo.cu.geocercas.entidades.BalizaGeocerca;
import com.galileo.cu.geocercas.entidades.BalizaRequest;
import com.galileo.cu.geocercas.entidades.Geocerca;
import com.galileo.cu.geocercas.entidades.GeocercaEstado;
import com.galileo.cu.geocercas.servicios.GeocercaServicio;
import com.galileo.cu.geocercas.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = GeocercaRestDocApplication.class)
@Slf4j
class GeocercaControllerTest {

    @Autowired
    private GeocercaServicio servicio;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void getGeocercas() throws Exception {
        BalizaRequest request = new BalizaRequest();
        request.setId(12345);
        request.setIdDataminer("2633");
        request.setIdElement("8409");

        BalizaGeocerca geocerca = createBalizaGeocerca(request);

        Mockito.when(servicio.getGeocercas(ArgumentMatchers.any(BalizaRequest.class))).thenReturn(geocerca);

        mockMvc.perform(post("/")
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(Attributes.attributes(Attributes.key("title").value("Estructura de la solicitud")),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Identificador de la baliza en base de datos"),
                                fieldWithPath("idDataminer").type(JsonFieldType.STRING).description("Identificador en el Dataminer"),
                                fieldWithPath("idElement").type(JsonFieldType.STRING).description("Identificador del elemento")),
                        responseFields(Attributes.attributes(Attributes.key("title").value("Estructura de la respuesta")),
                                fieldWithPath("idDataminer").type(JsonFieldType.STRING).description("Identificador en el DataMiner"),
                                fieldWithPath("idElement").type(JsonFieldType.STRING).description("Identificador del elemento"),
                                subsectionWithPath("_links").type(JsonFieldType.OBJECT).description("Enlaces relacionados con la entidad"),
                                fieldWithPath("noAsignadas").type(JsonFieldType.ARRAY).description("Arreglo de geocercas no asignadas"),
                                fieldWithPath("noAsignadas[].id").type(JsonFieldType.STRING).description("Identificador de la geocerca"),
                                fieldWithPath("noAsignadas[].index").type(JsonFieldType.STRING).description("Indice de la geocerca"),
                                fieldWithPath("noAsignadas[].name").type(JsonFieldType.STRING).description("Nombre de la geocerca"),
                                fieldWithPath("noAsignadas[].description").type(JsonFieldType.STRING).description("Descripcion de la geocerca"),
                                fieldWithPath("noAsignadas[].area").type(JsonFieldType.STRING).description("Area de la geocerca"),
                                fieldWithPath("noAsignadas[]._links").type(JsonFieldType.OBJECT).description("Enlaces relacionados con la entidad"),
                                subsectionWithPath("noAsignadas[]._links.add-geocerca").type(JsonFieldType.OBJECT).description("Enlace para agregar una geocerca"),
                                fieldWithPath("asignadas").type(JsonFieldType.ARRAY).description("Arreglo de geocercas asignadas"),
                                fieldWithPath("asignadas[].id").type(JsonFieldType.STRING).description("Identificador de la geocerca"),
                                fieldWithPath("asignadas[].index").type(JsonFieldType.STRING).description("Indice de la geocerca"),
                                fieldWithPath("asignadas[].name").type(JsonFieldType.STRING).description("Nombre de la geocerca"),
                                fieldWithPath("asignadas[].activa").type(JsonFieldType.BOOLEAN).description("Describe si la geocerca esta activa o no"),
                                fieldWithPath("asignadas[].bottomLeftLatitude").type(JsonFieldType.STRING).description("Coordenada de la geocerca"),
                                fieldWithPath("asignadas[].bottomLeftLongitude").type(JsonFieldType.STRING).description("Coordenada de la geocerca"),
                                fieldWithPath("asignadas[].topRightLatitude").type(JsonFieldType.STRING).description("Coordenada de la geocerca"),
                                fieldWithPath("asignadas[].topRightLongitude").type(JsonFieldType.STRING).description("Coordenada de la geocerca"),
                                fieldWithPath("asignadas[]._links").type(JsonFieldType.OBJECT).description("Enlaces relacionados con la entidad"),
                                subsectionWithPath("asignadas[]._links.desactivar-geocerca").type(JsonFieldType.OBJECT).description("Enlace para desactivar una geocerca").optional(),
                                subsectionWithPath("asignadas[]._links.activar-geocerca").type(JsonFieldType.OBJECT).description("Enlace para activar una geocerca").optional(),
                                subsectionWithPath("asignadas[]._links.delete-geocerca").type(JsonFieldType.OBJECT).description("Enlace para eliminar una geocerca").optional())));
    }

    @Test
    void getGeocercaEstado() throws Exception {
        Integer elementId = 8409;
        Integer dataminerId = 2633;

        GeocercaEstado estado = new GeocercaEstado();
        estado.setId(5964);
        estado.setDisplayValue("OK");
        estado.setValue("1");
        estado.setLastValueChange("2022-10-02 17:00:38");
        estado.setLastValueChangeUTC(1664722838000L);

        Mockito.when(servicio.getEstado(dataminerId, elementId)).thenReturn(estado);

        mockMvc.perform(get("/{elementId}/{dataminerId}/estado", elementId, dataminerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("elementId").description("Identificador del elemento (Numérico)"),
                                parameterWithName("dataminerId").description("Identificador en DataMiner (Numérico)")),
                        responseFields(Attributes.attributes(Attributes.key("title").value("Estructura de la respuesta")),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Identificador get parámetro"),
                                fieldWithPath("value").type(JsonFieldType.STRING).description("Valor del estado"),
                                fieldWithPath("displayValue").type(JsonFieldType.STRING).description("Valor del estado a mostrar"),
                                fieldWithPath("lastValueChange").type(JsonFieldType.STRING).description("Fecha de ultima modificación"),
                                fieldWithPath("lastValueChangeUTC").type(JsonFieldType.NUMBER).description("Timestamp de la fecha de ultima modificación (UTC)"))));
    }

    @Test
    void error() throws Exception {
        BalizaRequest request = new BalizaRequest();
        request.setId(12345);

        BalizaGeocerca geocerca = createBalizaGeocerca(request);

        Mockito.when(servicio.getGeocercas(ArgumentMatchers.any(BalizaRequest.class))).thenReturn(new BalizaGeocerca());

        mockMvc.perform(post("/")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    private static BalizaGeocerca createBalizaGeocerca(BalizaRequest request) {
        BalizaGeocerca result = new BalizaGeocerca();
        result.setIdDataminer(request.getIdDataminer());
        result.setIdElement(request.getIdElement());
        result.setAsignadas(Arrays.asList(buildGeocercaAsignada(1, true), buildGeocercaAsignada(2, false)));
        result.setNoAsignadas(Arrays.asList(buildGeocercaNoAsignada(3), buildGeocercaNoAsignada(4)));

        return result;
    }

    private static Geocerca buildGeocercaNoAsignada(int i) {
        Geocerca geocerca = new Geocerca();
        geocerca.setId(""+i);
        geocerca.setName("Geocerca "+i);
        geocerca.setDescription("Descripcion de la Geocerca "+i);
        geocerca.setArea("CIRCLE (41.33664752284821 -1.5195880780674886, 128432.8)");
        geocerca.setIndex(""+i);

        return geocerca;
    }

    private static Geocerca buildGeocercaAsignada(int i, boolean activa) {
        Geocerca geocerca = new Geocerca();
        geocerca.setId(""+i);
        geocerca.setName("Geocerca "+i);
        geocerca.setBottomLeftLatitude("402931058"+i);
        geocerca.setBottomLeftLongitude("4256766846"+i);
        geocerca.setTopRightLatitude("402936837"+i);
        geocerca.setTopRightLongitude("4256772625"+i);
        geocerca.setAsignada(true);
        geocerca.setActiva(activa);
        geocerca.setIndex(""+i);

        return geocerca;
    }

    @Test
    void addGeocerca() throws Exception {
        Integer elementId = 8409;
        Integer dataminerId = 2633;
        Integer index = 7;
        Mockito.doNothing().when(servicio).addGeocerca(elementId, dataminerId, index);

        mockMvc.perform(post("/{elementId}/{dataminerId}/{index}", elementId, dataminerId, index)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("elementId").description("Identificador del elemento (Numérico)"),
                                parameterWithName("dataminerId").description("Identificador en DataMiner (Numérico)"),
                                parameterWithName("index").description("Indice en la tabla (Numérico)"))));
    }

    @Test
    void activarGeocerca() throws Exception {
        Integer elementId = 8409;
        Integer dataminerId = 2633;
        Integer index = 7;
        Mockito.doNothing().when(servicio).sendParameter(elementId, dataminerId, 13010, index, "0");

        mockMvc.perform(put("/{elementId}/{dataminerId}/{index}/activar", elementId, dataminerId, index)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("elementId").description("Identificador del elemento"),
                                parameterWithName("dataminerId").description("Identificador en DataMiner"),
                                parameterWithName("index").description("Indice en la tabla"))));
    }

    @Test
    void desactivarGeocerca() throws Exception {
        Integer elementId = 8409;
        Integer dataminerId = 2633;
        Integer index = 7;
        Mockito.doNothing().when(servicio).sendParameter(elementId, dataminerId, 13012, index, "0");

        mockMvc.perform(put("/{elementId}/{dataminerId}/{index}/desactivar", elementId, dataminerId, index)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("elementId").description("Identificador del elemento"),
                                parameterWithName("dataminerId").description("Identificador en DataMiner"),
                                parameterWithName("index").description("Indice en la tabla"))));
    }

    @Test
    void deleteGeocerca() throws Exception {
        Integer elementId = 8409;
        Integer dataminerId = 2633;
        Integer index = 7;
        Mockito.doNothing().when(servicio).sendParameter(elementId, dataminerId, 13009, index, "1");

        mockMvc.perform(delete("/{elementId}/{dataminerId}/{index}", elementId, dataminerId, index)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("elementId").description("Identificador del elemento"),
                                parameterWithName("dataminerId").description("Identificador en DataMiner"),
                                parameterWithName("index").description("Indice en la tabla"))));
    }
}