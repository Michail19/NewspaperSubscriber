package com.ms.subscriptionservice.controller;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionResponseDTO;
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
    public SubscriptionResponseDTO getUserSubscription(@Argument Long id) {
        Subscription subscription = subscriptionService.getById(id);
        return new SubscriptionResponseDTO(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getMagazineId(),
                subscription.getStart_date(),
                subscription.getEnd_date(),
                subscription.getDuration_months(),
                subscription.getStatus()
        );
    }

    @QueryMapping
    public List<SubscriptionResponseDTO> getUserSubscriptions(@Argument Long id) {
        Subscription subscription = subscriptionService.getById(id);
        return new SubscriptionResponseDTO(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getMagazineId(),
                subscription.getStart_date(),
                subscription.getEnd_date(),
                subscription.getDuration_months(),
                subscription.getStatus()
        );
    }

    @MutationMapping
    public SubscriptionResponseDTO createSubscription(@Argument SubscriptionRequestDTO input) {
        Subscription subscription = subscriptionService.create(input);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription created id=" + subscription.getId() + ", " +
                "for user id=" + input.getUserId() + ", " +
                "for magazine id=" + input.getMagazineId()
        );
        return new SubscriptionResponseDTO(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getMagazineId(),
                subscription.getStart_date(),
                subscription.getEnd_date(),
                subscription.getDuration_months(),
                subscription.getStatus()
        );
    }

    @MutationMapping
    public SubscriptionResponseDTO updateSubscription(@Argument Long id, @Argument SubscriptionRequestDTO input) {
        Subscription subscription = subscriptionService.update(id, input);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription updated id=" + id + ", " +
                "for user id=" + input.getUserId() + ", " +
                "for magazine id=" + input.getMagazineId()
        );
        return new SubscriptionResponseDTO(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getMagazineId(),
                subscription.getStart_date(),
                subscription.getEnd_date(),
                subscription.getDuration_months(),
                subscription.getStatus()
        );
    }

    @MutationMapping
    public Boolean cancelSubscription(@Argument Long subscriptionId) {
        subscriptionService.cancel(subscriptionId);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription cancelled id=" + subscriptionId + ", "
        );
        return true;
    }
}
