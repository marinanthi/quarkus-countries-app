package com.example.deserializers;

import com.example.models.Country;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class CountryDeserializer extends JsonbDeserializer<Country> {

    public CountryDeserializer() {
        super(Country.class);
    }
}