package com.ms.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "f_name")
    private String first_name;

    @Column(name = "s_name")
    private String second_name;

    @Column(name = "t_name")
    private String third_name;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Timestamp registration_date;

    public Users(long id, String first_name, String second_name, String third_name, int age, Timestamp registration_date) {
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.third_name = third_name;
        this.age = age;
        this.registration_date = registration_date;
    }

    public Users() {}
}
