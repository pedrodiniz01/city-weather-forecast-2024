package com.example.demo.repository;

import com.example.demo.data.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String name);

    Optional<City> findByName(String cityName);
}
