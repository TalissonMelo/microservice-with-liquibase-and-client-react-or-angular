package com.talissonmelo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceUserLiquibaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUserLiquibaseApplication.class, args);
	}

}
