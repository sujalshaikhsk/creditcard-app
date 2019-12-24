package com.strickers.creditcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CreditcardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditcardApplication.class, args);
	}
}
