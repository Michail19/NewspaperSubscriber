package com.ms.userservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
        // Здесь можно вызвать бизнес-логику, например обновить статус подписки
    }
}
