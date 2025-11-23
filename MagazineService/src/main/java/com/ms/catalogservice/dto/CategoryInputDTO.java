package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInputDTO {
    private String name;

    public CategoryInputDTO(String name) {
        this.name = name;
    }
}
