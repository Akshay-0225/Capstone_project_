package com.capstone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Task_Capstone", version = "1.0", 
description = "Documentation API Gateway v1.0",
contact = @Contact(name ="Capstone",email="capstone@gmail.com" ),
license = @License(name="Apache2.0")))
@SpringBootApplication
@EnableDiscoveryClient
public class TaskCapstoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskCapstoneApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}