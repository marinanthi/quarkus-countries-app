package com.example.mappers;

import com.example.models.Country;
import com.example.models.CountryDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MappersTest {

    @Test
    public void shouldMapCarToDto() {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(new CountryDto.Name("Greece"));
        countryDto.setCapital(List.of("Athens"));
        countryDto.setContinents(List.of("Europe"));
        countryDto.setCca2("GR");

        Country c = CountryMapStruct.INSTANCE.countryDtoToCountry(countryDto);

        assertNotNull(c);
        assertEquals("Greece", c.getCountryName());
        assertEquals("Athens", c.getCapital());
        assertEquals("GR", c.getCountryCode());
        assertEquals("Europe", c.getContinent());
    }
}
