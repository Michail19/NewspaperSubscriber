package com.ms.subscriptionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SubscriptionRequestDTO {
    private int userId;
    private int magazineId;
    private int durationMonths;
    private Timestamp startDate;

    public SubscriptionRequestDTO() {
    }

    public SubscriptionRequestDTO(int userId, int magazineId, int durationMonths, Timestamp startDate) {
        this.userId = userId;
        this.magazineId = magazineId;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
    }

}
