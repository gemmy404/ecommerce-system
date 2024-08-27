package com.ecommerce.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableCaching
@EnableAspectJAutoProxy
@OpenAPIDefinition(
        info = @Info(
                title = "Ecommerce API",
                version = "v1",
                description = "Ecommerce System API"
        )
)
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MultiConfig {

    @Bean
    public GroupedOpenApi setUpGroupedOpenApi() {
        String packages =  "com.ecommerce.system.controller";
        return GroupedOpenApi.builder().group("Ecommerce API").packagesToScan(packages).build();
    }
}
