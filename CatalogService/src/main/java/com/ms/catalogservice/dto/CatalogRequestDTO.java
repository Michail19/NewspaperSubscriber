package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class CatalogRequestDTO {
    private String firstName;
    private String secondName;
    private String thirdName;
    private int age;

    public CatalogRequestDTO(String firstName, String secondName, String thirdName, int age, Timestamp registrationDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
    }

    public CatalogRequestDTO() {}
}
