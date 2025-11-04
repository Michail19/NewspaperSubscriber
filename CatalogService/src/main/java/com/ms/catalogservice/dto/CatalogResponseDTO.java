package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class CatalogResponseDTO {
    private String firstName;
    private String secondName;
    private String thirdName;
    private int age;
    private Timestamp registrationDate;

    public CatalogResponseDTO(String firstName, String secondName, String thirdName, int age, Timestamp registrationDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public CatalogResponseDTO() {}
}
