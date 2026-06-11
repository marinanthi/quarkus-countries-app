package com.example.services;

public class ImperativeCountryService {
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
