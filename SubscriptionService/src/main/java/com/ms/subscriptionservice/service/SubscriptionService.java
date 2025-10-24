package com.ms.subscriptionservice.service;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
import com.ms.subscriptionservice.model.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    public Subscription create(SubscriptionRequestDTO dto) {
        return null;
    }

    public Subscription update(int id, SubscriptionRequestDTO dto) {
        return null;
    }

    public Subscription delete(SubscriptionRequestDeleteDTO dto) {
        return null;
    }

    public List<Subscription> getByUserId(int userId) {
        return null;
    }

    public boolean cancel(int subscriptionId) {
        return false;
    }

    public Subscription extend(int id, int extraMonths) {
        return null;
    }
}
