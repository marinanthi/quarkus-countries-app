package com.example.clients;

import com.example.mappers.CountryMapStruct;
import com.example.models.Country;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Singleton
public class ReactiveCountriesClient {
    @Inject
    @RestClient
    RestCountriesClient client;

    //CountryMapStruct.INSTANCE.countryDtoToCountry(countryDto);
    public Uni<Country> getFromClientByNameReactive(String name) {
        return client.getCountry(
                        name,
                        "name,cca2,capital,continents"
                )
                .map(countries -> CountryMapStruct.INSTANCE.countryDtoToCountry(countries[0]));
    }
}
