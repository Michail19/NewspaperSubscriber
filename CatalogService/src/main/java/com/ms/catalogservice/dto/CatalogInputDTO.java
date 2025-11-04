package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogInputDTO {
    private String title;
    private String description;
    private Double price;
    private String link;
    private Long categoryId;
    private Long seriesId;

    public CatalogInputDTO() {}

    public CatalogInputDTO(String title, String description, Double price, String link, Long categoryId, Long seriesId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.link = link;
        this.categoryId = categoryId;
        this.seriesId = seriesId;
    }
}
