package com.ms.subscriptionservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubscriptionRequestDeleteDTO {
    private int userId;
    private int magazineId;

    public SubscriptionRequestDeleteDTO() {
    }

    public SubscriptionRequestDeleteDTO(int userId, int magazineId) {
        this.userId = userId;
        this.magazineId = magazineId;
    }

}
