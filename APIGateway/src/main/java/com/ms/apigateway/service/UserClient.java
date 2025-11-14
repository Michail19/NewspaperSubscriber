package com.ms.apigateway.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserClient {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public UserClient(WebClient.Builder builder, ObjectMapper objectMapper) {
        this.webClient = builder.baseUrl("http://user-service:8082/graphql").build();
        this.objectMapper = objectMapper;
    }

    public Object getUserById(String id) {
        // Используем переменные вместо интерполяции строк
        String query = "query GetUser($id: String!) { getUser(id: $id) { id firstName secondName thirdName age registrationDate } }";

        Map<String, Object> variables = Map.of("id", id);

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", variables);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object addUser(Object input) {
        String mutation = """
        mutation AddUser($input: UserInput!) {
            addUser(input: $input) {
                id
                firstName
                secondName
                thirdName
                age
                registrationDate
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

    public Object addUserWithSerialization(Map<String, Object> userData) {
        String mutation = """
        mutation AddUser($input: UserInput!) {
            addUser(input: $input) {
                id
                firstName
                secondName
                thirdName
                age
                registrationDate
            }
        }
        """;

        Map<String, Object> variables = Map.of("input", userData);

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", variables);

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
