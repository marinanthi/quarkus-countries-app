package com.example.resources;

import com.example.models.UpdateVisited;
import com.example.services.CountryService;
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
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryByName(@PathParam("name") String name) {
        try {
            return countryService.getByName(name);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path("/{name}/visited")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVisited(@PathParam("name") String name, UpdateVisited request) {
//        try {
            return countryService.updateVisitedStatus(name, request);
//        } catch (Exception e) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
    }
}