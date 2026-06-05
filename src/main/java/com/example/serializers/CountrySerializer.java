package com.example.serializers;

import com.example.models.Country;
import io.quarkus.kafka.client.serialization.JsonbSerializer;

public class CountrySerializer extends JsonbSerializer<Country> {
}