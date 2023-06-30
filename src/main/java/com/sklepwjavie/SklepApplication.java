package com.sklepwjavie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
public class SklepApplication {

	public static void main(String[] args) {
		SpringApplication.run(SklepApplication.class, args);
	}

}
