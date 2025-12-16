package com.ms.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Измените с /graphql на /**
                        .allowedOrigins(
                                "http://localhost:3000",      // Браузер
                                "http://frontend:80",         // Docker контейнер frontend
                                "http://localhost:80",        // Прямой доступ
                                "http://frontend:3000"        // На всякий случай
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);  // Добавьте maxAge
            }
        };
    }
}