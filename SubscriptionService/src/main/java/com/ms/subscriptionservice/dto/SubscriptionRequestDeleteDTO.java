package com.ms.subscriptionservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubscriptionRequestDeleteDTO {
    private Long subscriptionId;
    private Long userId;
    private Long magazineId;

    public SubscriptionRequestDeleteDTO() {
    }

    public SubscriptionRequestDeleteDTO(Long userId, Long magazineId, Long subscriptionId) {
        this.userId = userId;
        this.magazineId = magazineId;
        this.subscriptionId = subscriptionId;
    }

}