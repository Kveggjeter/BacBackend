package com.bac.bacbackend.domain.model;

public enum StringResource {
    COCITY("Answer with only the city you associate the text with. If no city, then state. If no state, country."),
    COCATEGORY("Answer with only the category this text should be place in. Choose only between: Business, Crime, Culture, Politics, Science, Sports.");

    private final String value;

    StringResource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

