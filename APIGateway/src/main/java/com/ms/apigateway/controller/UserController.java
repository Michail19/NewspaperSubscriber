package com.ms.apigateway.controller;

import com.ms.apigateway.service.SubscriptionClient;
import com.ms.apigateway.service.UserClient;
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
    public Object getUser(String id) {
        Object user = userClient.getUserById(id);
        Object subscriptions = subscriptionClient.getSubscriptionsByUser(id);
        return new java.util.HashMap<>() {{
            put("user", user);
            put("subscriptions", subscriptions);
        }};
    }
}
