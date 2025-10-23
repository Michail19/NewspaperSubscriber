package com.ms.subscriptionservice.controller;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.service.MessagePublisher;
import com.ms.subscriptionservice.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SubscriptionController {
    MessagePublisher messagePublisher;
    SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionRequestDTO dto) {
        Subscription subscription = subscriptionService.create(dto);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription created: userId=" + dto.getUserId() + ", magazineId=" + dto.getMagazineId()
        );
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/update")
    public ResponseEntity<Subscription> updateSubscription(@RequestBody SubscriptionRequestDTO dto) {
        Subscription subscription = subscriptionService.update(dto);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription updated: userId=" + dto.getUserId() + ", magazineId=" + dto.getMagazineId()
        );
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/delete")
    public ResponseEntity<Subscription> deleteSubscription(@RequestBody SubscriptionRequestDeleteDTO dto) {
        Subscription subscription = subscriptionService.delete(dto);
        messagePublisher.sendSubscriptionCreatedMessage(
                "Subscription deleted: userId=" + dto.getUserId() + ", magazineId=" + dto.getMagazineId()
        );
        return ResponseEntity.ok(subscription);
    }
}
