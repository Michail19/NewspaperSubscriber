package com.ms.subscriptionservice.service;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    public Subscription create(SubscriptionRequestDTO dto) {
        return null;
    }

    public Subscription update(long id, SubscriptionRequestDTO dto) {
        return null;
    }

    public List<Subscription> getByUserId(long userId) {
        return null;
    }

    public void cancel(SubscriptionRequestDeleteDTO delete) {
        Subscription subscription = subscriptionRepository.findById(delete.getSubscriptionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));

        subscriptionRepository.delete(subscription);
    }
}
