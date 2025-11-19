package com.ms.apigateway.service;

import com.ms.apigateway.dto.GraphQLResponseDTO;
import com.ms.apigateway.util.GraphQLHelper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://user-service:8082/graphql").build();
    }

    public Object getUserById(String id) {
        String query = """
            query GetUser($id: ID!) {
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

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", query);
        payload.put("variables", Map.of("id", id));

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "getUser");
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

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "addUser");
    }

    public Object updateUser(String id, Object input) {
        String mutation = """
            mutation UpdateUser($id: ID!, $input: UserInput!) {
                updateUser(id: $id, input: $input) {
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
        payload.put("variables", Map.of("id", id, "input", input));

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractData(response, "updateUser");
    }

    public Boolean removeUser(String id) {
        String mutation = """
            mutation RemoveUser($id: ID!) {
                removeUser(id: $id)
            }
            """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("query", mutation);
        payload.put("variables", Map.of("id", id));

        GraphQLResponseDTO response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(GraphQLResponseDTO.class)
                .block();

        return extractBooleanData(response, "removeUser");
    }

    private Object extractData(GraphQLResponseDTO response, String fieldName) {
        if (response == null || response.hasErrors()) {
            // Обработка ошибок
            return null;
        }

        if (response.getData() instanceof Map) {
            Map<String, Object> data = (Map<String, Object>) response.getData();
            return data.get(fieldName);
        }

        return null;
    }

    private Boolean extractBooleanData(GraphQLResponseDTO response, String fieldName) {
        Object data = extractData(response, fieldName);
        return data instanceof Boolean ? (Boolean) data : false;
    }
}
