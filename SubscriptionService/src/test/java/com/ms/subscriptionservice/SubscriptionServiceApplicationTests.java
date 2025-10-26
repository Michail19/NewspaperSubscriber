package com.ms.subscriptionservice;

import com.ms.subscriptionservice.controller.SubscriptionController;
import com.ms.subscriptionservice.dto.SubscriptionRequestDTO;
import com.ms.subscriptionservice.dto.SubscriptionRequestDeleteDTO;
import com.ms.subscriptionservice.model.Subscription;
import com.ms.subscriptionservice.service.MessagePublisher;
import com.ms.subscriptionservice.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceApplicationTests {

    private SubscriptionService subscriptionService;
    private MessagePublisher messagePublisher;
    private SubscriptionController subscriptionController;

    @BeforeEach
    void setUp() {
        subscriptionService = mock(SubscriptionService.class);
        messagePublisher = mock(MessagePublisher.class);
        subscriptionController = new SubscriptionController(subscriptionService, messagePublisher);
    }

    @Test
    void testGetUserSubscriptions() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);

        when(subscriptionService.getById(1L)).thenReturn(subscription);

        Subscription result = subscriptionController.getUserSubscriptions(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(subscriptionService, times(1)).getById(1L);
        verifyNoInteractions(messagePublisher);
    }

    @Test
    void testCreateSubscription() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        dto.setUserId(10L);
        dto.setMagazineId(20L);

        Subscription subscription = new Subscription();
        subscription.setId(1L);

        when(subscriptionService.create(dto)).thenReturn(subscription);

        Subscription result = subscriptionController.createSubscription(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(messagePublisher, times(1)).sendSubscriptionCreatedMessage(messageCaptor.capture());

        String message = messageCaptor.getValue();
        assertTrue(message.contains("Subscription created id=1"));
        assertTrue(message.contains("for user id=10"));
        assertTrue(message.contains("for magazine id=20"));
    }

    @Test
    void testUpdateSubscription() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        dto.setUserId(10L);
        dto.setMagazineId(20L);

        Subscription subscription = new Subscription();
        subscription.setId(1L);

        when(subscriptionService.update(1L, dto)).thenReturn(subscription);

        Subscription result = subscriptionController.updateSubscription(1L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(messagePublisher, times(1)).sendSubscriptionCreatedMessage(messageCaptor.capture());

        String message = messageCaptor.getValue();
        assertTrue(message.contains("Subscription updated id=1"));
        assertTrue(message.contains("for user id=10"));
        assertTrue(message.contains("for magazine id=20"));
    }

    @Test
    void testCancelSubscription() {
        SubscriptionRequestDeleteDTO dto = new SubscriptionRequestDeleteDTO();
        dto.setSubscriptionId(1L);
        dto.setUserId(10L);
        dto.setMagazineId(20L);

        doNothing().when(subscriptionService).cancel(dto);

        subscriptionController.cancelSubscription(dto);

        verify(subscriptionService, times(1)).cancel(dto);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(messagePublisher, times(1)).sendSubscriptionCreatedMessage(messageCaptor.capture());

        String message = messageCaptor.getValue();
        assertTrue(message.contains("Subscription cancelled id=1"));
        assertTrue(message.contains("for user id=10"));
        assertTrue(message.contains("for magazine id=20"));
    }
}
