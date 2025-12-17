package com.ms.apigateway.controller;

import com.ms.apigateway.service.SubscriptionClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class SubscriptionController {

    private final SubscriptionClient subscriptionClient;

    public SubscriptionController(SubscriptionClient subscriptionClient) {
        this.subscriptionClient = subscriptionClient;
    }

    @QueryMapping
    public Mono<Object> getUserSubscription(@Argument String id) {
        return subscriptionClient.getSubscriptionByUser(id);
    }

    @QueryMapping
    public Mono<Object> getUserSubscriptions(@Argument String id) {
        return subscriptionClient.getSubscriptionsByUser(id);
    }

    @MutationMapping
    public Mono<Object> createSubscription(@Argument("input") Object input) {
        return subscriptionClient.createSubscription(input);
    }

    @MutationMapping
    public Mono<Object> cancelSubscription(@Argument String subscriptionId) {
        return subscriptionClient.cancelSubscription(subscriptionId);
    }

    @MutationMapping
    public Mono<Object> updateSubscription(@Argument String id, @Argument("input") Object input) {
        return subscriptionClient.updateSubscription(id, input);
    }
}
