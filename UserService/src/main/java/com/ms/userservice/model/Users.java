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
    private String firstName;

    @Column(name = "s_name")
    private String secondName;

    @Column(name = "t_name")
    private String thirdName;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    public Users(long id, String firstName, String secondName, String thirdName, int age, Timestamp registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public Users() {}
}
