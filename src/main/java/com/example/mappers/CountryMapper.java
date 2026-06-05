package com.example.mappers;

import com.example.models.Country;
import com.example.models.CountryDto;

public class CountryMapper {
    public static Country countryMapper(CountryDto countryDto) {
        String mappedName = countryDto.getName().getCommon();
        String mappedCapital = countryDto.getCapital().get(0);
        String mappedContinents = countryDto.getContinents().get(0);
        String cca2 = countryDto.getCca2();
        return new Country(mappedName, mappedCapital, cca2, mappedContinents);
    }
}