package com.ms.subscriptionservice.controller;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.service.MessagePublisher;
import com.ms.subscriptionservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final MessagePublisher messagePublisher;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, MessagePublisher messagePublisher) {
        this.subscriptionService = subscriptionService;
        this.messagePublisher = messagePublisher;
    }

    @QueryMapping
    public List<Subscription> getUserSubscriptions(@Argument int userId) {
        return subscriptionService.getByUserId(userId);
    }

    @MutationMapping
    public Subscription createSubscription(@Argument SubscriptionRequestDTO input) {
        Subscription subscription = subscriptionService.create(input);
        messagePublisher.sendSubscriptionCreatedMessage("Subscription created for userId=" + input.getUserId());
        return subscription;
    }

    @MutationMapping
    public Subscription updateSubscription(@Argument int id, @Argument SubscriptionRequestDTO input) {
        Subscription subscription = subscriptionService.update(id, input);
        messagePublisher.sendSubscriptionCreatedMessage("Subscription updated id=" + id);
        return subscription;
    }

    @MutationMapping
    public void cancelSubscription(@Argument int subscriptionId) {
        subscriptionService.cancel(subscriptionId);
        messagePublisher.sendSubscriptionCreatedMessage("Subscription cancelled id=" + subscriptionId);
    }
}
