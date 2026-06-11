package com.example.clients;

import com.example.models.CountryDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/countries/v5")
@RegisterRestClient(configKey = "countries-api")
public interface RestCountriesClient {

    @GET
    @Path("/names.common/{name}")
    @ClientHeaderParam(name = "Authorization", value = "Bearer " + "${countries-api.key}")
    Uni<CountryDto[]> getCountry(
            @PathParam("name") String name,
            @QueryParam("fields") String fields
    );
}