package com.ms.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CatalogClient {
    private final WebClient webClient;

    public CatalogClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8083/graphql").build();
    }

    public Object getCatalogById(String id) {
        String query = "{ getCatalogById(id: \"" + id + "\") { id title description price link } }";
        return webClient.post()
                .bodyValue("{\"query\":\"" + query + "\"}")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
