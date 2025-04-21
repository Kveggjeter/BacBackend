package com.bac.bacbackend.domain.common;

public class Boundaries {

    public Boundaries() {}

    public boolean coordinatesChecker(String[] list) {
        float x = Float.parseFloat(list[4]);
        float y = Float.parseFloat(list[5]);
        String country = list[1].toLowerCase();
        String continent = (list[2]).toLowerCase();

        CoordinateBoundaries coordinateBoundaries = CoordinateBoundaries.continentCheck(country, continent);
        if (coordinateBoundaries == null) return true;

        if (coordinateBoundaries == CoordinateBoundaries.AUSTRALIA)
            return x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                    && y <= coordinateBoundaries.getMaxLonOne() && y >= coordinateBoundaries.getMinLonOne()
                    || x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                    && y <= coordinateBoundaries.getMaxLonTwo() && y >= coordinateBoundaries.getMinLonTwo();
        else
            return x <= coordinateBoundaries.getMaxLat() && x >= coordinateBoundaries.getMinLat()
                    && y <= coordinateBoundaries.getMaxLonOne() && y >= coordinateBoundaries.getMinLonOne();
    }

}
