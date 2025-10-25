package com.ms.subscriptionservice.service;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.model.SubscriptionStatus;
import com.ms.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription create(SubscriptionRequestDTO dto) {
        Subscription subscription = new Subscription();

        subscription.setUserId(dto.getUserId());
        subscription.setMagazineId(dto.getMagazineId());
        subscription.setStart_date(Timestamp.valueOf(LocalDateTime.now()));
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setDuration_months(dto.getDurationMonths());

        if (dto.getDurationMonths() == -1) {
            subscription.setEnd_date(null);
        } else {
            LocalDateTime endDate = LocalDateTime.now().plusMonths(dto.getDurationMonths());
            subscription.setEnd_date(Timestamp.valueOf(endDate));
        }

        subscription.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        subscription.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        return subscriptionRepository.save(subscription);
    }

    public Subscription update(long id, SubscriptionRequestDTO dto) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));

        subscription.setDuration_months(dto.getDurationMonths());
        subscription.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        if (dto.getDurationMonths() == -1) {
            subscription.setEnd_date(null);
        } else {
            LocalDateTime newEndDate = LocalDateTime.now().plusMonths(dto.getDurationMonths());
            subscription.setEnd_date(Timestamp.valueOf(newEndDate));
        }

        return subscriptionRepository.save(subscription);
    }

    public Subscription getById(long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));
    }

    public void cancel(SubscriptionRequestDeleteDTO dto) {
        Subscription subscription = subscriptionRepository.findById(dto.getSubscriptionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подписка не найдена"));

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        subscription.setEnd_date(Timestamp.valueOf(LocalDateTime.now()));

        subscriptionRepository.save(subscription);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateFromSchedule() {
        List<Subscription> all = subscriptionRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Subscription s : all) {
            if (s.getEnd_date() != null && s.getEnd_date().toLocalDateTime().isBefore(now)) {
                s.setStatus(SubscriptionStatus.EXPIRED);
                s.setUpdated_at(Timestamp.valueOf(now));
                subscriptionRepository.save(s);
            }
        }
    }
}
