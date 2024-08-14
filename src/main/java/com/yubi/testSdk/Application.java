package com.yubi.testSdk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yubi.*","com.yubidrive.*"})
@OpenAPIDefinition(info = @Info(title = "API-Doc for testSdk", version = "0.0.1-SNAPSHOT", description = "This is a swagger document for the APIs of testSdk"))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
