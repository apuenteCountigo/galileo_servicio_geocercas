package com.galileo.cu.geocercas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.CommandLineRunner;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ServicioGeocercasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServicioGeocercasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("**************************************");
		System.out.println("GEOCERCAS V1.1-24-10-09 15:21");
	}

}
