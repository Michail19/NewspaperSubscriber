package com.ms.subscriptionservice.model;

import java.sql.Date;

public class Subscription {
    private int id;
    private int user_id;
    private int magazine_id;
    private Date start_date;
    private Date end_date;
    private double duration_months;
    private SubscriptionStatus status;
    private Date created_at;
    private Date updated_at;

    public Subscription(int id, int user_id, int magazine_id,
                        Date start_date, Date end_date, double duration_months,
                        SubscriptionStatus status, Date created_at, Date updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.magazine_id = magazine_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.duration_months = duration_months;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMagazine_id() {
        return magazine_id;
    }

    public void setMagazine_id(int magazine_id) {
        this.magazine_id = magazine_id;
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
