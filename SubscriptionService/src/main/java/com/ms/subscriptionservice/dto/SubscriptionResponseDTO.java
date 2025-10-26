package com.ms.subscriptionservice.dto;

import com.ms.subscriptionservice.model.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SubscriptionResponseDTO {
    private Long id;
    private Long userId;
    private Long magazineId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer durationMonths;
    private SubscriptionStatus status;

    public SubscriptionResponseDTO() {}

    public SubscriptionResponseDTO(Long id, Long userId, Long magazineId, Timestamp startDate, Timestamp endDate,
                                   Integer durationMonths, SubscriptionStatus status) {
        this.id = id;
        this.userId = userId;
        this.magazineId = magazineId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationMonths = durationMonths;
        this.status = status;
    }
}
