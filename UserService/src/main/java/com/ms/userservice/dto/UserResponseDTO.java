package com.ms.userservice.dto;

import java.sql.Timestamp;

public class UserResponseDTO {
    private String firstName;
    private String secondName;
    private String thirdName;
    private int age;
    private Timestamp registrationDate;

    public UserResponseDTO(String firstName, String secondName, String thirdName, int age, Timestamp registrationDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public UserResponseDTO() {}
}
