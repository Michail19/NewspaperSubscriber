package com.ms.apigateway.service;

import com.ms.apigateway.dto.GraphQLResponseDTO;
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

    public Object getSubscriptionByUser(String userId) {
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

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "getUserSubscription");
    }

    public Object getSubscriptionsByUser(String userId) {
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

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "getUserSubscriptions");
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

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "createSubscription");
    }

    public Boolean cancelSubscription(String subscriptionId) {
        String mutation = """
            mutation CancelSubscription($subscriptionId: ID!) {
                cancelSubscription(subscriptionId: $subscriptionId)
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("subscriptionId", subscriptionId));

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractBooleanData(response, "cancelSubscription");
    }

    public Object updateSubscription(String id, Object input) {
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

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "updateSubscription");
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

    private Boolean extractBooleanData(GraphQLResponseDTO response, String fieldName) {
        Object data = extractData(response, fieldName);
        if (data instanceof Boolean b) {
            return b;
        }
        return null;
    }
}
