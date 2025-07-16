package com.stageprojet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OffreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffreServiceApplication.class, args);
	}

}
