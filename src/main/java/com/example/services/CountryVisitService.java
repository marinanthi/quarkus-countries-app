package com.example.services;

import com.example.models.Country;
import com.example.repositories.CountryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class CountryVisitService {

    @Inject
    @Channel("visited")
    Emitter<String> visited;

    @Inject
    @Channel("not-visited")
    Emitter<String> notVisited;
    @Inject
    CountryRepository countryRepository;

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
}
