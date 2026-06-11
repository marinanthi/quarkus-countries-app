package com.example.mappers;

import com.example.models.Country;
import com.example.models.CountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.*;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface CountryMapStruct {
    CountryMapStruct INSTANCE = getMapper(CountryMapStruct.class);

    @Mapping(source = "name.common", target = "countryName")
    @Mapping(source = "cca2", target = "countryCode")
    @Mapping(source = "capital", target = "capital")
    @Mapping(source = "continents", target = "continent")
    @Mapping(target = "visited", constant = "false")
    Country countryDtoToCountry(CountryDto source);

    default String map(List<String> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }
}
