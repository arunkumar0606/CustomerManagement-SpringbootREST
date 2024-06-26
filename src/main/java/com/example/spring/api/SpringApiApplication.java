package com.example.spring.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
//@EnableJpaRepositories("com.example.spring.api.repository")
//@EntityScan("com.example.spring.api.entity")
public class SpringApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringApiApplication.class, args);
		System.out.println("App imported !");
	}

}
