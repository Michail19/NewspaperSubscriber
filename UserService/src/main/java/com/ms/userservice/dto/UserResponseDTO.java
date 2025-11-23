package com.ms.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UserResponseDTO {
    private long id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private int age;
    private String registrationDate;

    public UserResponseDTO(long id, String firstName, String secondName, String thirdName,
                           int age, String registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public UserResponseDTO() {}
}
