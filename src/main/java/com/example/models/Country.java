package com.example.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@JsonbPropertyOrder({
        "countryName",
        "capital",
        "countryCode",
        "continent"
})

@MongoEntity(collection = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {
    @NotBlank(message = "Country Name cannot be blank")
    private String countryName;

    @NotBlank(message = "Capital cannot be blank")
    private String capital;

    @NotBlank(message = "Country Code cannot be blank")
    private String countryCode;

    @NotBlank(message = "Continent cannot be blank")
    private String continent;

    private boolean visited;
}