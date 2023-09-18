package com.myblog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


//starting point of the project
@SpringBootApplication
public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

	@Bean //difrent liberery
   public ModelMapper modelMapper(){
		return new ModelMapper();
   }
}

