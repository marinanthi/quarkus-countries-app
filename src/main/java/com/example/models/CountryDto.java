package com.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CountryDto {
    @JsonProperty("name")
    private Name name;

    @JsonProperty("capital")
    private List<String> capital;

    @JsonProperty("cca2")
    private String cca2;

    @JsonProperty("continents")
    private List<String> continents;

    public static class Name {
        @JsonProperty("common")
        private String common;

        public Name(String common) {
            this.common = common;
        }

        public String getCommon() {
            return common;
        }
    }

    public CountryDto() {
    }

    public Name getName() {
        return name;
    }

    public String getCca2() {
        return cca2;
    }

    public List<String> getCapital() {
        return capital;
    }

    public List<String> getContinents() {
        return continents;
    }
}
