package com.example.clients;

import com.example.models.Country;
import com.example.models.CountryDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.example.mappers.Mappers.countryMapper;

public class CountriesClient {

    public static Country getFromClientByName(String name) {
        Response res = ClientBuilder
                .newClient()
                .target("https://restcountries.com/v3.1/name/" + name + "?fields=name,cca2,capital,continents")
                .request(MediaType.APPLICATION_JSON)
                .get();
        CountryDto[] countries = res.readEntity(CountryDto[].class);
        return countryMapper(countries[0]);
    }
}
