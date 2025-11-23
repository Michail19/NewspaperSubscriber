package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponseDTO {
    private Long id;
    private String name;

    public CategoryResponseDTO() {}
}
