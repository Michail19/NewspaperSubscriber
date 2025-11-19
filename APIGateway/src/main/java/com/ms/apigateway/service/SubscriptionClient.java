package com.ms.apigateway.service;

import com.ms.apigateway.util.GraphQLHelper;
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

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return GraphQLHelper.extractSingle(response, "getUserSubscriptions");
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

    public Object cancelSubscription(String subscriptionId) {
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
                .bodyToMono(Object.class)
                .block();
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

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
