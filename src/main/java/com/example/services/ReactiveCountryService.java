package com.example.services;

import com.example.models.Country;
import com.example.models.UpdateVisited;
import com.example.repositories.CountryRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ReactiveCountryService {

    @Inject
    CountryRepository countryRepo;

    @Inject
    ReactiveRedisDataSource reactiveRedis;

    @Inject
    @Channel("visit-tracker-out")
    Emitter<Country> tracker;

    @Inject
    @Channel("visited")
    Emitter<String> visited;

    @Inject
    @Channel("not-visited")
    Emitter<String> notVisited;

//    @Inject
//    RedisDataSource redis;

//    @Inject
//    ReactiveCountriesClient client;

    Logger logger = LoggerFactory.getLogger(ReactiveCountryService.class.getName());

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

    //  consumes from visit-tracker topic and sends to visited or not-visited channel
    @Incoming("visit-tracker-in")
    public void trackCountries(Country country) {
        String msg = "Country " + country.getCountryName() + " visited status updated to " + country.isVisited();

        System.out.println(country);
        if (country.isVisited()) {
            visited.send(msg);
        } else {
            notVisited.send(msg);
        }
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
}