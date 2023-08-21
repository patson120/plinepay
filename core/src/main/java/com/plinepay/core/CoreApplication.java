package com.plinepay.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.security.web.firewall.HttpFirewall;
// import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	// @Bean
	// public HttpFirewall allowUrlSemicolonHttpFirewall() {
	// 	StrictHttpFirewall firewall = new StrictHttpFirewall();
	// 	firewall.setAllowSemicolon(true);
	// 	return firewall;
	// }

	@Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
