package com.ms.catalogservice.repository;

import com.ms.catalogservice.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findById(Long employeeId);

}
