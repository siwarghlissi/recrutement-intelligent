package com.stageprojet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CandidatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidatServiceApplication.class, args);
	}

}
