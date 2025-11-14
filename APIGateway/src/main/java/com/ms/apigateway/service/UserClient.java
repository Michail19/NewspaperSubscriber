package com.ms.apigateway.service;

import com.ms.apigateway.dto.UserResponseDTO;
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

    public UserResponseDTO getUserById(String id) {

        String query = """
        query GetUser($id: String!) {
            getUser(id: $id) {
                id
                firstName
                secondName
                thirdName
                age
                registrationDate
            }
        }
        """;

        Map<String, Object> variables = Map.of("id", id);

        Map<String, Object> payload = Map.of(
                "query", query,
                "variables", variables
        );

        Map response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Если есть ошибки → возвращаем null
        if (response.containsKey("errors")) {
            return null;
        }

        Map<String, Object> data = (Map<String, Object>) response.get("data");

        if (data == null || data.get("getUser") == null) {
            return null;
        }

        Map<String, Object> userMap = (Map<String, Object>) data.get("getUser");

        return new UserResponseDTO(
                longValue(userMap.get("id")),
                (String) userMap.get("firstName"),
                (String) userMap.get("secondName"),
                (String) userMap.get("thirdName"),
                intValue(userMap.get("age")),
                (String) userMap.get("registrationDate")
        );
    }

    private Long longValue(Object val) {
        return val == null ? null : Long.parseLong(val.toString());
    }

    private Integer intValue(Object val) {
        return val == null ? null : Integer.parseInt(val.toString());
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
