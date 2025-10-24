package com.ms.subscriptionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SubscriptionRequestDTO {
    private long userId;
    private long magazineId;
    private int durationMonths;
    private Timestamp startDate;

    public SubscriptionRequestDTO() {
    }

    public SubscriptionRequestDTO(long userId, long magazineId, int durationMonths, Timestamp startDate) {
        this.userId = userId;
        this.magazineId = magazineId;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
    }

}
