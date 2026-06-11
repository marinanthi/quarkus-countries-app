package com.example.resources;

import com.example.models.Country;
import com.example.models.UpdateVisited;
import com.example.services.CountryService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("countries")
public class CountriesResource {

    @Inject
    CountryService countryService;

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

//    @GET
//    @Path("/{name}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getCountryByName(@PathParam("name") String name) {
//            return countryService.getByName(name);
//    }

//    @PATCH
//    @Path("/{name}/visited")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateVisited(@PathParam("name") String name, UpdateVisited request) {
//            return countryService.updateVisitedStatus(name, request);
//    }
}