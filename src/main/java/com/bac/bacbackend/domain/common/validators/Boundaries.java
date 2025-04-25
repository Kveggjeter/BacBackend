package com.bac.bacbackend.domain.common.validators;

public class Boundaries {

    public Boundaries() {}

    public boolean coordinatesChecker(String[] promptResult) {
        float x = Float.parseFloat(promptResult[4]);
        float y = Float.parseFloat(promptResult[5]);
        String country = promptResult[1].toLowerCase();
        String continent = (promptResult[2]).toLowerCase();

        CoordinateBoundaries coordinateBoundaries = CoordinateBoundaries.continentCheck(country, continent);
        if (coordinateBoundaries == null) return true;

        if (coordinateBoundaries == CoordinateBoundaries.OCEANIA)
            return x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                    && y <= coordinateBoundaries.getMaxLonOne() && y >= coordinateBoundaries.getMinLonOne()
                    || x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                    && y <= coordinateBoundaries.getMaxLonTwo() && y >= coordinateBoundaries.getMinLonTwo();
        else
            return x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                    && y <= coordinateBoundaries.getMaxLonOne() && y >= coordinateBoundaries.getMinLonOne();
    }
}
