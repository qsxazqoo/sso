package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = { "com.example.controller", "com.example.configuration", 
//		"com.example.services"})
//@EnableJpaRepositories(basePackages={"com.example.dao"})
//@EntityScan("com.example.entity")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringSecurityJwtApplication extends SpringBootServletInitializer{
	
	
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SpringSecurityJwtApplication.class);
//    }
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

}
