package com.ms.subscriptionservice.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId")
    private int userId;

    @Column(name = "magazineId")
    private int magazineId;

    @Column(name = "start_date")
    private Timestamp start_date;

    @Column(name = "end_date")
    private Timestamp end_date;

    @Column(name = "duration_months")
    private int duration_months;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubscriptionStatus status;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    public Subscription(int id, int userId, int magazineId,
                        Timestamp start_date, Timestamp end_date, int duration_months,
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

    public Subscription() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }

    public int getDuration_months() {
        return duration_months;
    }

    public void setDuration_months(int duration_months) {
        this.duration_months = duration_months;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
