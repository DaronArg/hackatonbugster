package com.ganatan.backend_java.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Road To Catalogo API", version = "1.0.0", description = "API documentation for the Backend Spring Boot project.")
)
public class OpenApiConfig {
}