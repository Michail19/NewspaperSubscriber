package com.ms.apigateway.controller;

import com.ms.apigateway.service.UserClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class UserController {

    private final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @QueryMapping
    public Mono<Object> getUser(@Argument String id) {
        return userClient.getUserById(id);
    }

    @MutationMapping
    public Mono<Object> addUser(@Argument("input") Object input) {
        return userClient.addUser(input);
    }

    @MutationMapping
    public Mono<Object> updateUser(@Argument String id, @Argument("input") Object input) {
        return userClient.updateUser(id, input);
    }

    @MutationMapping
    public Mono<Object> removeUser(@Argument String id) {
        return userClient.removeUser(id);
    }
}
