package com.ms.catalogservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    public Catalog(long id, String title, String description, double price, String link,
                   Category category, Series series) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.link = link;
        this.category = category;
        this.series = series;
    }

    public Catalog() {
    }
}
