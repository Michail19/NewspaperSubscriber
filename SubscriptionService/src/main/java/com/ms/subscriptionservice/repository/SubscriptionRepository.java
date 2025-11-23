package com.ms.subscriptionservice.repository;

import com.ms.subscriptionservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findById(Long employeeId);
    List<Subscription> findByUserId(Long userId);

}
