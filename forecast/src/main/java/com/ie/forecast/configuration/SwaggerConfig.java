package com.ie.forecast.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI forecastOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Forecast API")
                        .description("Forecast API - Forecast application for Ireland")
                        .version("v0.0.1")
                );
    }

}
