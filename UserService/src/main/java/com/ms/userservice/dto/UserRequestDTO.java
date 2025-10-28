package com.ms.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UserRequestDTO {
    private String firstName;
    private String secondName;
    private String thirdName;
    private int age;

    public UserRequestDTO(String firstName, String secondName, String thirdName, int age, Timestamp registrationDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
    }

    public UserRequestDTO() {}
}
