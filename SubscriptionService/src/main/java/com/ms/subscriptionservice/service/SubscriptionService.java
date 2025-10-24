package com.ms.subscriptionservice.service;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.model.SubscriptionStatus;
import com.ms.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

import java.util.List;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    public Subscription create(SubscriptionRequestDTO dto) {
        Subscription subscription = new Subscription();

        subscription.setUserId(dto.getUserId());
        subscription.setMagazineId(dto.getMagazineId());
        subscription.setStart_date(new Timestamp(System.currentTimeMillis()));
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setDuration_months(dto.getDurationMonths());
        subscription.setEnd_date(null);

        subscriptionRepository.save(subscription);

        return subscription;
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
