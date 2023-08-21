package com.plinepay.payementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(scanBasePackages={"com.plinepay.payementservice", "com.plinepay.core"})
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Payment API", version = "1.0.0", description = "Documentation for payment service"))
public class PayementServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PayementServiceApplication.class, args);
	}

}
