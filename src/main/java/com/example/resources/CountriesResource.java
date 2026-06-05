package com.example.resources;

import com.example.models.Country;
import com.example.models.UpdateVisited;
import com.example.services.CountryService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("rest-client/countries")
public class CountriesResource {

    @Inject
    CountryService countryService;

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryByName(@PathParam("name") String name) {
        return countryService.getByName(name);
    }

    @PATCH
    @Path("/{name}/visited")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateVisited(@PathParam("name") String name, UpdateVisited request) {
        countryService.updateVisitedStatus(name, request);
    }
}