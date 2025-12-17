package com.ms.apigateway.service;

import com.ms.apigateway.dto.GraphQLResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubscriptionClient {
    private final WebClient webClient;

    public SubscriptionClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://subscription-service:8081/graphql").build();
    }

    public Mono<Object> getSubscriptionByUser(String userId) {
        String query = """
            query GetUserSubscription($userId: ID!) {
                getUserSubscription(id: $userId) {
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
        payload.put("query", query);
        payload.put("variables", Map.of("userId", userId));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getUserSubscription"));
    }

    public Mono<Object> getSubscriptionsByUser(String userId) {
        String query = """
            query GetUserSubscriptions($userId: ID!) {
                getUserSubscriptions(id: $userId) {
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
        payload.put("query", query);
        payload.put("variables", Map.of("userId", userId));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "getUserSubscriptions"));
    }

    public Mono<Object> createSubscription(Object input) {
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
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "createSubscription"));
    }

    public Mono<Object> cancelSubscription(String subscriptionId) {
        String mutation = """
            mutation CancelSubscription($subscriptionId: ID!) {
                cancelSubscription(subscriptionId: $subscriptionId)
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("subscriptionId", subscriptionId));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "cancelSubscription"));
    }

    public Mono<Object> updateSubscription(String id, Object input) {
        String mutation = """
            mutation UpdateSubscription($id: ID!, $input: UpdateSubscriptionInput!) {
                updateSubscription(id: $id, input: $input) {
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
        payload.put("variables", Map.of("id", id, "input", input));

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .map(resp -> extractData(resp, "updateSubscription"));
    }

    private Object extractData(GraphQLResponseDTO response, String fieldName) {
        if (response == null) {
            return null;
        }

        if (response.hasErrors()) {
            return null;
        }

        if (response.getData() instanceof Map<?, ?> data) {
            return data.getOrDefault(fieldName, null);
        }

        return null;
    }
}
