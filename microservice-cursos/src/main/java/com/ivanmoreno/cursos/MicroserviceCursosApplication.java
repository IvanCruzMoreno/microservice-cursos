package com.ivanmoreno.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.ivanmoreno.commons.models.entity",
	         "com.ivanmoreno.cursos.models.entity"})
public class MicroserviceCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCursosApplication.class, args);
	}

}
