package com.ms.subscriptionservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "magazineId")
    private Long magazineId;

    @Column(name = "start_date")
    private Timestamp start_date;

    @Column(name = "end_date")
    private Timestamp end_date;

    @Column(name = "duration_months")
    private Integer duration_months;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubscriptionStatus status;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    public Subscription(Long id, Long userId, Long magazineId,
                        Timestamp start_date, Timestamp end_date, Integer duration_months,
                        SubscriptionStatus status, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.userId = userId;
        this.magazineId = magazineId;
        this.start_date = start_date;
        this.end_date = end_date;
        this.duration_months = duration_months;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Subscription() {}
}
