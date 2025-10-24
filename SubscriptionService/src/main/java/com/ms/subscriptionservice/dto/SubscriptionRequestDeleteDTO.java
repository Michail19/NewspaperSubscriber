package com.ms.subscriptionservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubscriptionRequestDeleteDTO {
    private long subscriptionId;
    private long userId;
    private long magazineId;

    public SubscriptionRequestDeleteDTO() {
    }

    public SubscriptionRequestDeleteDTO(long userId, long magazineId, long subscriptionId) {
        this.userId = userId;
        this.magazineId = magazineId;
        this.subscriptionId = subscriptionId;
    }

}