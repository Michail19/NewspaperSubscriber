package com.ms.subscriptionservice.service;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
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

    public Subscription update(int id, SubscriptionRequestDTO dto) {
        return null;
    }

    public List<Subscription> getByUserId(int userId) {
        return null;
    }

    public void cancel(long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Сотрудник не найден"));

        subscriptionRepository.delete(subscription);
    }
}
