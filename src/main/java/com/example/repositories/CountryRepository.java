package com.example.repositories;

import com.example.models.Country;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CountryRepository implements PanacheMongoRepository<Country> {

    public Uni<Country> findByNameReactive(String countryName) {
        return Uni.createFrom().item(
                () -> find("countryName", countryName).firstResult()
        );
    }

    // imperative
    public Optional<Country> findByName(String countryName) {
        return find("countryName", countryName).firstResultOptional();
    }
}