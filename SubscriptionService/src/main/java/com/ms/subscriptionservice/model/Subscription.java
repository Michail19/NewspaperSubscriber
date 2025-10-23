package com.ms.subscriptionservice.model;

import jakarta.persistence.*;

import java.sql.Date;

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
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "duration_months")
    private double duration_months;

    @Column(name = "status")
    private SubscriptionStatus status;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;

    public Subscription(int id, int userId, int magazineId,
                        Date start_date, Date end_date, double duration_months,
                        SubscriptionStatus status, Date created_at, Date updated_at) {
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getDuration_months() {
        return duration_months;
    }

    public void setDuration_months(double duration_months) {
        this.duration_months = duration_months;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
