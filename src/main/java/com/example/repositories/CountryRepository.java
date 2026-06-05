package com.example.repositories;

import com.example.models.Country;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CountryRepository implements PanacheMongoRepository<Country> {
    public Optional<Country> findByName(String countryName) {
        return find("countryName", countryName).firstResultOptional();
    }
}