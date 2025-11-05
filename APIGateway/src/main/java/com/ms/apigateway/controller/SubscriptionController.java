package com.ms.apigateway.controller;

import com.ms.apigateway.service.SubscriptionClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SubscriptionController {

    private final SubscriptionClient subscriptionClient;

    public SubscriptionController(SubscriptionClient subscriptionClient) {
        this.subscriptionClient = subscriptionClient;
    }

    @QueryMapping
    public Object getUserSubscriptions(String id) {
        return subscriptionClient.getSubscriptionsByUser(id);
    }

    @MutationMapping
    public Object createSubscription(@Argument("input") Object input) {
        return subscriptionClient.createSubscription(input);
    }
}
