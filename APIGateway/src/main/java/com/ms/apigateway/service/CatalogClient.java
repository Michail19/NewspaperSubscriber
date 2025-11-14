package com.ms.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CatalogClient {
    private final WebClient webClient;

    public CatalogClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://catalog-service:8083/graphql").build();
    }

    public Object getCatalogById(String id) {
        String query = "{ getCatalogById(id: \"" + id + "\") { id title description price link } }";
        return webClient.post()
                .bodyValue("{\"query\":\"" + query + "\"}")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object addCatalog(Object input) {
        String mutation = """
            mutation ($input: CatalogInput!) {
                addCatalog(input: $input) {
                    id
                    title
                    description
                    price
                }
            }
            """;

        return webClient.post()
                .bodyValue("{\"query\":\"" + mutation.replace("\"", "\\\"") + "\",\"variables\":{\"input\":" + toJson(input) + "}}")
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
