package com.ecommerce.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableCaching
@EnableAspectJAutoProxy
@OpenAPIDefinition(
        info = @Info(
                title = "Product API",
                version = "v1",
                description = "CRUD Management System API"
        )
)
public class MultiConfig {

    @Bean
    public GroupedOpenApi setUpGroupedOpenApi() {
        String packages =  "com.springboot.ecombackend1main.controller";
        return GroupedOpenApi.builder().group("Product API").packagesToScan(packages).build();
    }
}
