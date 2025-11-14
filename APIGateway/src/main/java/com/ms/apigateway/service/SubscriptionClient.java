package com.ms.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SubscriptionClient {
    private final WebClient webClient;

    public SubscriptionClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://subscription-service:8081/graphql").build();
    }

    public Object getSubscriptionsByUser(String userId) {
        String query = "{ getUserSubscriptions(id: \"" + userId + "\") { id magazineId durationMonths status startDate endDate } }";
        return webClient.post()
                .bodyValue("{\"query\":\"" + query + "\"}")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object createSubscription(Object input) {
        String mutation = """
            mutation ($input: CreateSubscriptionInput!) {
                createSubscription(input: $input) {
                    id
                    userId
                    magazineId
                    durationMonths
                    status
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
