package com.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    @JsonProperty("name")
    private Name name;

    @JsonProperty("capital")
    private List<String> capital;

    @JsonProperty("cca2")
    private String cca2;

    @JsonProperty("continents")
    private List<String> continents;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        @JsonProperty("common")
        private String common;
    }
}
