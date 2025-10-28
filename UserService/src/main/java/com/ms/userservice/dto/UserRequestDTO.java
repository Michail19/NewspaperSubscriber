package com.ms.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UserRequestDTO {
    private long id;
    private String first_name;
    private String second_name;
    private String third_name;
    private int age;
    private Timestamp registration_date;

    public UserRequestDTO(long id, String first_name, String second_name, String third_name, int age, Timestamp registration_date) {
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.third_name = third_name;
        this.age = age;
        this.registration_date = registration_date;
    }

    public UserRequestDTO() {}
}
