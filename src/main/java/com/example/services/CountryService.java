package com.example.services;

import io.quarkus.redis.datasource.RedisDataSource;
import com.example.clients.ReactiveCountriesClient;
import com.example.models.Country;
import com.example.models.UpdateVisited;
import com.example.repositories.CountryRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CountryService {

    @Inject
    CountryRepository countryRepo;

    @Inject
    ReactiveRedisDataSource reactiveRedis;

    @Inject
    @Channel("visit-tracker-out")
    Emitter<Country> tracker;

//    @Inject
//    RedisDataSource redis;

//    @Inject
//    ReactiveCountriesClient client;

    Logger logger = LoggerFactory.getLogger(CountryService.class.getName());

    // reactive approach for the GET endpoint
    public Uni<Country> getByNameReactive(String name) {
        System.out.println("SERVICE METHOD getByNameReactive ENTERED");
        return reactiveRedis.value(Country.class).get(name)
                .onItem().invoke(country ->
                        logger.info("Country found in Redis cache: " + country)
                )
                .onItem().ifNull().switchTo(() ->
                        getFromMongoAndCache(name)
                );
    }

    public Uni<Country> getFromMongoAndCache(String name) {
        System.out.println("SERVICE METHOD getFromMongoAndCache ENTERED");

        return countryRepo.findByNameReactive(name).onItem().invoke(country ->
                        logger.info("Country found in Mongo: " + country)
                )
                .onItem().ifNotNull().invoke(country -> {
                    reactiveRedis.value(Country.class).set(name, country);
                })
                .onItem().ifNull().failWith(new RuntimeException("Country not found"));
    }

    public Uni<Country> updateVisitedStatusReactive (String name, UpdateVisited request) {
        return countryRepo.findByNameReactive(name).onItem().invoke(country ->
                        country.setVisited(request.visited())
                )
                .onItem().invoke(country ->
                        tracker.send(country)
                )
                .onItem().invoke(country ->
                        logger.info("Country is sent to Kafka topic visit-tracker")
                )
                .onItem().ifNull().failWith(new RuntimeException("Country can't be produced bc country not found"));

    }

    // rest countries API method
//   public Uni<Country> getFromClientSaveAndCache(String name) {
//        System.out.println("SERVICE METHOD getFromClientSaveAndCache ENTERED");
//        return client.getFromClientByNameReactive(name)
//                .onItem().invoke(country ->
//                        logger.info("Fetching from country API: " + country)
//                )
//                .onItem().invoke(country -> {
//                    redis.value(Country.class).set(name, country);
//                })
//                .onItem().invoke(country -> {
//                    countryRepo.persist(country);
//                });
//   }

    // imperative approach
//    public Response getByName(String name) {
//    public Response getByName(String name) {
//        Country mongoCountry = countryRepo.findByName(name).orElse(null);
//        Country redisCountry = redis.value(Country.class).get(name);
//
//        if (redisCountry != null) {
//            logger.info("Country found in Redis cache: " + redisCountry);
//            return Response.ok(redisCountry).build();
//        } else if (mongoCountry != null) {
//            logger.info("Country found in database: " + mongoCountry);
//            redis.value(Country.class).set(name, mongoCountry);
//            return Response.ok(mongoCountry).build();
//        } else {
//            logger.info("Fetching country from API...");
//            Country country = getFromClientByName(name);
//            countryRepo.persist(country);
//            redis.value(Country.class).set(name, country);
//            return Response.ok(country).build();
//        }
//    }

//    public Response updateVisitedStatus (String name, UpdateVisited request) {
//        Country country = countryRepo.findByName(name).orElse(null);
//
//        if (country != null) {
//            country.setVisited(request.visited());
//            tracker.send(country);
//        } else {
//            Country newCountry = getFromClientByName(name);
//            countryRepo.persist(newCountry);
//            Country c = countryRepo.findByName(name).orElse(null);
//            c.setVisited(request.visited());
//            tracker.send(c);
//        }
//
//        logger.info("Country is sent to Kafka topic visit-tracker");
//        return Response.ok(country).build();
//    }
}