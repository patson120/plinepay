package com.plinepay.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(scanBasePackages={"com.plinepay.authservice", "com.plinepay.core"})
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Authentication API", version = "1.0.0", description = "Documentation for authentication service"))
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
