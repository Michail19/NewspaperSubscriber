package com.ms.apigateway.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubscriptionClient {
    private final WebClient webClient;

    public SubscriptionClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://subscription-service:8081/graphql").build();
    }

    public Object getSubscriptionsByUser(String userId) {
        String query = """
            query GetUserSubscriptions($userId: String!) {
                getUserSubscriptions(id: $userId) {
                    id
                    magazineId
                    durationMonths
                    status
                    startDate
                    endDate
                }
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", Map.of("userId", userId));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object createSubscription(Object input) {
        String mutation = """
            mutation CreateSubscription($input: CreateSubscriptionInput!) {
                createSubscription(input: $input) {
                    id
                    userId
                    magazineId
                    durationMonths
                    status
                    startDate
                    endDate
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
