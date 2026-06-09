package com.example.mappers;

import com.example.models.Country;
import com.example.models.CountryDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MappersTest {

    @Test
    void countryMapperTest() {
        CountryDto countryFromApi = new CountryDto();
        countryFromApi.setName(new CountryDto.Name("Greece"));
        countryFromApi.setCapital(List.of("Athens"));
        countryFromApi.setContinents(List.of("Europe"));
        countryFromApi.setCca2("GR");

        Country country = Mappers.countryMapper(countryFromApi);

        assertNotNull(country);
        assertEquals("Greece", country.getCountryName());
        assertEquals("Athens", country.getCapital());
        assertEquals("GR", country.getCountryCode());
        assertEquals("Europe", country.getContinent());
    }
}
