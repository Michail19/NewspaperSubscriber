package com.ms.subscriptionservice;

import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.model.SubscriptionStatus;
import com.ms.subscriptionservice.repository.SubscriptionRepository;
import com.ms.subscriptionservice.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceTest {

    private SubscriptionRepository subscriptionRepository;
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        subscriptionRepository = mock(SubscriptionRepository.class);
        subscriptionService = new SubscriptionService(subscriptionRepository);
    }

    @Test
    void testCreateSubscriptionWithDuration() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        dto.setUserId(1L);
        dto.setMagazineId(2L);
        dto.setDurationMonths(3);

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Subscription result = subscriptionService.create(dto);

        assertNotNull(result.getStart_date());
        assertNotNull(result.getEnd_date());
        assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
        assertEquals(3, result.getDuration_months());
        assertEquals(1L, result.getUserId());
        assertEquals(2L, result.getMagazineId());
    }

    @Test
    void testCreateSubscriptionUnlimitedDuration() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        dto.setUserId(1L);
        dto.setMagazineId(2L);
        dto.setDurationMonths(-1);

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Subscription result = subscriptionService.create(dto);

        assertNull(result.getEnd_date());
        assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
    }
}
