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
        if (dto.getDurationMonths() != -1) subscription.setEnd_date(null);
        else subscription.setEnd_date(new Timestamp((System.currentTimeMillis() +
                (long) dto.getDurationMonths() * 30 * 24 * 60 * 60)));

        subscriptionRepository.save(subscription);

        return subscription;
    }

    public Subscription update(long id, SubscriptionRequestDTO dto) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));

        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setDuration_months(dto.getDurationMonths());
        if (dto.getDurationMonths() != -1) subscription.setEnd_date(null);
        else subscription.setEnd_date(new Timestamp((System.currentTimeMillis() +
                (long) dto.getDurationMonths() * 30 * 24 * 60 * 60)));

        subscriptionRepository.save(subscription);

        return subscription;
    }

    public Subscription getById(long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));
    }

    public void cancel(SubscriptionRequestDeleteDTO dto) {
        Subscription subscription = subscriptionRepository.findById(dto.getSubscriptionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setDuration_months(-2);
        subscription.setEnd_date(new Timestamp(System.currentTimeMillis()));

        subscriptionRepository.save(subscription);
    }

    public void updateFromSchedule() {
        // Обновление по расписанию
    }
}
