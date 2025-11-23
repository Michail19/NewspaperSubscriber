package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String link;
    private CategoryResponseDTO category;
    private SeriesResponseDTO series;

    public CatalogResponseDTO() {}
}
