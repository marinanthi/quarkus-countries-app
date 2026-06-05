package com.example.services;

import com.example.clients.CountryClient;
import com.example.models.Country;
import com.example.models.CountryDto;
import com.example.models.UpdateVisited;
import com.example.repositories.CountryRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.example.mappers.CountryMapper.countryMapper;

@ApplicationScoped
public class CountryService {

    @Inject
    @RestClient
    CountryClient countryClient;

    @Inject
    CountryRepository countryRepo;

    @Inject
    RedisDataSource redis;

    @Inject
    @Channel("visit-tracker-out")
    Emitter<Country> tracker;

    Logger logger = LoggerFactory.getLogger(CountryService.class.getName());

    public Response getByName(String name) {
        Country mongoCountry = countryRepo.findByName(name).orElse(null);
        Country redisCountry = redis.value(Country.class).get(name);

        if (redisCountry != null) {
            logger.info("Country found in Redis cache: " + redisCountry);
            return Response.ok(redisCountry).build();
        } else if (mongoCountry != null) {
            logger.info("Country found in database: " + mongoCountry);
            redis.value(Country.class).set(name, mongoCountry);
            return Response.ok(mongoCountry).build();
        } else {
            logger.info("Fetching country from API...");
            List<CountryDto> countries = countryClient.getCountryByName(name, "cca2,name,continents,capital");
            Country newCountry = countryMapper(countries.get(0));
            countryRepo.persist(newCountry);
            redis.value(Country.class).set(name, newCountry);
            return Response.ok(newCountry).build();
        }
    }

    public Response updateVisitedStatus (String name, UpdateVisited request) {
        Country country = countryRepo.findByName(name).orElse(null);

        if (country != null) {
            country.setVisited(request.visited());
            tracker.send(country);
        } else {
            List<CountryDto> countries = countryClient.getCountryByName(name, "cca2,name,continents,capital");
            Country newCountry = countryMapper(countries.get(0));
            countryRepo.persist(newCountry);
            Country c = countryRepo.findByName(name).orElse(null);
            c.setVisited(request.visited());
            tracker.send(c);
        }

        logger.info("Country is sent to Kafka topic visit-tracker");
        return Response.ok(country).build();
    }
}