package com.bac.bacbackend.domain.model;

public enum StringResource {
    COMMAND("Answer with one(always) city, country, region(middle-east, central-africa, midwest etc) and continent you associate the text with. Also put it in one of these categories: {Business,Crime,Culture,Politics,Science,Sports} Also give coordinates to place it in Leaflet (max 3 decimals). Send back this format: city/country/region/continent/category/x/y");

    private final String value;

    StringResource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

