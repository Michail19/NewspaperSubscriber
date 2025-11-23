package com.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriesInputDTO {
    private String name;

    public SeriesInputDTO(String name) {
        this.name = name;
    }
}
