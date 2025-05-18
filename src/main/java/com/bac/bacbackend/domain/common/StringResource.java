package com.bac.bacbackend.domain.common;


/**
 * ENUM class holding the command given to the AI. Hard coded as an ENUM, but might be placed into a .yaml file later.
 */

public enum StringResource {
    COMMAND("Answer with one(always) city, country and continent(Oceania, Europe, Africa, North-America etc) you associate the text with. Also put it in one of these categories: {Business,Crime,Culture,Politics,Science,Sports} Also give coordinates to place it in Leaflet (max 3 decimals), for reference: Sydney is x = -33.883 y = 151.168 and Honolulu is x = 21.313 y = -157.862. Make sure to have that in mind when setting coordinates. Send back this format (very important, never respond with other format): city/country/continent/category/x/y");

    private final String value;

    StringResource(String value) {this.value = value;}
    public String getValue() {return value;}
}

