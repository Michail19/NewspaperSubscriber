package com.ms.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SubscriptionClient {
    private final WebClient webClient;

    public SubscriptionClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8081/graphql").build();
    }

    public Object getSubscriptionsByUser(String userId) {
        String query = "{ getUserSubscriptions(id: \"" + userId + "\") { id magazineId durationMonths status startDate endDate } }";
        return webClient.post()
                .bodyValue("{\"query\":\"" + query + "\"}")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
