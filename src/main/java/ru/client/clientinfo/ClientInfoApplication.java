package ru.client.clientinfo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@EnableCaching
@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Customer contacts API", 
		description = "Customer contacts", 
		version = "1.0.0", 
		contact = @Contact(name = "Rakovchen Bogdan", 
		email = "bogdanrakovchen@mail.ru")))
public class ClientInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientInfoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
