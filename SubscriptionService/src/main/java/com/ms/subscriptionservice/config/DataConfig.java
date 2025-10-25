package com.ms.subscriptionservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ms.subscriptionservice.repository")
public class DataConfig {
}
