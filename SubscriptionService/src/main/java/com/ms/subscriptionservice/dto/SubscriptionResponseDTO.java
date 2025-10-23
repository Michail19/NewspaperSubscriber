package com.ms.subscriptionservice.dto;

import com.ms.subscriptionservice.model.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SubscriptionResponseDTO {
    private int id;
    private int userId;
    private int magazineId;
    private Timestamp startDate;
    private Timestamp endDate;
    private int durationMonths;
    private SubscriptionStatus status;

    public SubscriptionResponseDTO() {}

    public SubscriptionResponseDTO(int id, int userId, int magazineId, Timestamp startDate, Timestamp endDate,
                                   int durationMonths, SubscriptionStatus status) {
        this.id = id;
        this.userId = userId;
        this.magazineId = magazineId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationMonths = durationMonths;
        this.status = status;
    }
}
