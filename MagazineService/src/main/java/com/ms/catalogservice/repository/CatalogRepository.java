package com.ms.catalogservice.repository;

import com.ms.catalogservice.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findById(Long catalogId);

}
