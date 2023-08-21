package com.plinepay.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(scanBasePackages={"com.plinepay.accountservice", "com.plinepay.core"})
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Account API", version = "1.0.0", description = "Documentation for account service"))
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
