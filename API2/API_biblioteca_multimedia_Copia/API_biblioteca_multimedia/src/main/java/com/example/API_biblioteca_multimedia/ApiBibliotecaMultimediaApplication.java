package com.example.API_biblioteca_multimedia;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiBibliotecaMultimediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBibliotecaMultimediaApplication.class, args);
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Biblioteca Multimedia API")
                        .description("API REST para app de contenido multimedia")
                        .contact(new Contact()
                                .name("Alberto Andujar")
                                .email("albertoandu56@gmail.com")
                                .url("https://portal.edu.gva.es/iesmaciaabela"))
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
