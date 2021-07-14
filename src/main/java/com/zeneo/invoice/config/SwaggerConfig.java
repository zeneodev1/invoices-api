package com.zeneo.invoice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new
                Info().title("Invoices API Docs").version("0.1"))
                .addSecurityItem(new SecurityRequirement().addList("Json Web Token"))
                .components(new Components().addSecuritySchemes("Json Web Token",
                        new SecurityScheme().name("Json Web Token").type(SecurityScheme.Type.HTTP).scheme("bearer")));
    }

}
