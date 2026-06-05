package com.example.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@JsonbPropertyOrder({
        "countryName",
        "capital",
        "countryCode",
        "continent"
})

@MongoEntity(collection = "countries")
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

    public Country() {
    }

    public Country(String countryName, String capital, String countryCode, String continent) {
        this.countryName = countryName;
        this.capital = capital;
        this.countryCode = countryCode;
        this.continent = continent;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCapital() {
        return capital;
    }

    public String getContinent() {
        return continent;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String toString() {
        return "Country: " + countryName + ", Capital: " + capital + ", Country Code: " + countryCode + ", Continent: " + continent;
    }
}