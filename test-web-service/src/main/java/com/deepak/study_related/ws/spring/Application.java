package com.deepak.study_related.ws.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Run with mvn spring-boot:run or java -jar target/<name of jar> Run with
 * http://localhost:8080/greeting
 * 
 * @author abrahd2
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}