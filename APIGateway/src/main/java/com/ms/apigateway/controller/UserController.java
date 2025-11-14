package com.ms.apigateway.controller;

import com.ms.apigateway.service.SubscriptionClient;
import com.ms.apigateway.service.UserClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserClient userClient;
    private final SubscriptionClient subscriptionClient;

    public UserController(UserClient userClient, SubscriptionClient subscriptionClient) {
        this.userClient = userClient;
        this.subscriptionClient = subscriptionClient;
    }

    @QueryMapping
    public Object getUser(@Argument String id) {
        return userClient.getUserById(id);
    }

    @MutationMapping
    public Object addUser(@Argument("input") Object input) {
        return userClient.addUser(input);
    }
}
