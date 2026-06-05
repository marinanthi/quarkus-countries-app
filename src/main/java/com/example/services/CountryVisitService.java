package com.example.services;

import com.example.models.Country;
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

    //  consumes from visit-tracker topic and sends to visited or not-visited channel
    @Incoming("visit-tracker-in")
    public void trackCountries(Country country) {
        String msg = "Country " + country.getCountryName() + " visited status updated to " + country.getVisited();

        if (country.getVisited()) {
            visited.send(msg);
        } else {
            notVisited.send(msg);
        }
    }
}
