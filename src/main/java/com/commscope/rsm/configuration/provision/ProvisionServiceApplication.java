package com.commscope.rsm.configuration.provision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProvisionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvisionServiceApplication.class, args);
	}

}
