package com.ms.apigateway.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class CatalogClient {
    private final WebClient webClient;

    public CatalogClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://catalog-service:8083/graphql").build();
    }

    public Object getCatalogById(String id) {
        String query = """
            query GetCatalogById($id: String!) {
                getCatalogById(id: $id) {
                    id
                    title
                    description
                    price
                    link
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", Map.of("id", id));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object addCatalog(Object input) {
        String mutation = """
            mutation AddCatalog($input: CatalogInput!) {
                addCatalog(input: $input) {
                    id
                    title
                    description
                    price
                    link
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    private String toJson(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
