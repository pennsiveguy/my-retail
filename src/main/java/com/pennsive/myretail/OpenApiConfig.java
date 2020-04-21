package com.pennsive.myretail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
 
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("MyRetail API").description(
                        "This is a REST API for retrieving basic product data from the MyRetail corporation."
                        + "<br>&copy;2020 Pennsive Consulting, LLC"));
    }
}
