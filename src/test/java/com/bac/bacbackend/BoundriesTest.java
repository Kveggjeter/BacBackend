package com.bac.bacbackend;

import com.bac.bacbackend.domain.common.validators.CoordinateBoundaries;

public class BoundriesTest {

    public static void main(String[] args) {
        String country = "Canada";
        String continent = "North-America";
        float x = 49.282F;
        float y = -55.678F;
        CoordinateBoundaries coordinateBoundaries = CoordinateBoundaries.continentCheck(continent.toLowerCase(), country.toLowerCase());

        if (coordinateBoundaries == null) System.out.println("Her var det null");
        if (x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                && y <= coordinateBoundaries.getMaxLonOne() && y >= coordinateBoundaries.getMinLonOne())
            System.out.println("Looks to be true");
        else System.out.println("Looks to be false");
    }
}
