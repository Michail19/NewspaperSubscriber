package com.ms.subscriptionservice.controller;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
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
    public List<Subscription> getUserSubscriptions(@Argument long userId) {
        return subscriptionService.getByUserId(userId);
    }

    @MutationMapping
    public Subscription createSubscription(@Argument SubscriptionRequestDTO input) {
        Subscription subscription = subscriptionService.create(input);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription created id=" + subscription.getId() + ", " +
                "for user id=" + input.getUserId() + ", " +
                "for magazine id=" + input.getMagazineId()
        );
        return subscription;
    }

    @MutationMapping
    public Subscription updateSubscription(@Argument long id, @Argument SubscriptionRequestDTO input) {
        Subscription subscription = subscriptionService.update(id, input);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription updated id=" + id + ", " +
                "for user id=" + input.getUserId() + ", " +
                "for magazine id=" + input.getMagazineId()
        );
        return subscription;
    }

    @MutationMapping
    public void cancelSubscription(@Argument SubscriptionRequestDeleteDTO delete) {
        subscriptionService.cancel(delete);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription cancelled id=" + delete.getSubscriptionId() + ", " +
                "for user id=" + delete.getUserId() + ", " +
                "for magazine id=" + delete.getMagazineId()
        );
    }
}
