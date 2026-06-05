package com.example.clients;

import com.example.models.CountryDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.*;

@Path("/v3.1")
@RegisterRestClient(configKey = "countries-api")
public interface CountryClient {
    @GET
    @Path("/name/{name}")
    List<CountryDto> getCountryByName(@PathParam("name") String name,
                                      @QueryParam("fields") String fields);

    @GET
    @Path("/capital/{capital}")
    List<CountryDto> getCountryByCapital(@PathParam("capital") String capital,
                                         @QueryParam("fields") String fields);
}