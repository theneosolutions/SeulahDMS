package com.example.Seulah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Seulah")
public class SeulahApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeulahApplication.class, args);
	}
}
