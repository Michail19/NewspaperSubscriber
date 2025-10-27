package com.ms.userservice.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class Users {
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private int age;
    private Timestamp registration_date;
    private List<Integer> subscriptions;

    public Users(long id, String surname, String name, String patronymic, int age, Timestamp registration_date) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.registration_date = registration_date;
    }

    public Users() {}
}
