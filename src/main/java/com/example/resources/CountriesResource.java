package com.example.resources;

import com.example.models.Country;
import com.example.models.UpdateVisited;
import com.example.services.ReactiveCountryService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("countries")
public class CountriesResource {

    @Inject
    ReactiveCountryService countryService;

    @GET
    @Path("/reactive/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Country> getCountryByNameReactive(@PathParam("name") String name) {
        return countryService.getByNameReactive(name);
    }

    @PATCH
    @Path("/reactive/{name}/visited")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Country> updateVisited(@PathParam("name") String name, UpdateVisited request) {
            return countryService.updateVisitedStatusReactive(name, request);
    }
}