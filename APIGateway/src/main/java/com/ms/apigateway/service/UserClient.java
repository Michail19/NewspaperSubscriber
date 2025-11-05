package com.ms.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8082/graphql").build();
    }

    public Object getUserById(String id) {
        String query = "{ getUser(id: \"" + id + "\") { id firstName secondName thirdName age registrationDate } }";
        return webClient.post()
                .bodyValue("{\"query\":\"" + query + "\"}")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
