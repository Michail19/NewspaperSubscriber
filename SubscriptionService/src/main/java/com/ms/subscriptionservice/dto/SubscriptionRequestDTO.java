package com.ms.subscriptionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SubscriptionRequestDTO {
    private Long userId;
    private Long magazineId;
    private Integer durationMonths;
    private Timestamp startDate;

    public SubscriptionRequestDTO() {
    }

    public SubscriptionRequestDTO(Long userId, Long magazineId, Integer durationMonths, Timestamp startDate) {
        this.userId = userId;
        this.magazineId = magazineId;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
    }

}
