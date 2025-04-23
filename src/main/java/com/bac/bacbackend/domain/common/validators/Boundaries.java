package com.bac.bacbackend.domain.common.validators;

public class Boundaries {

    public Boundaries() {}

    /**
     * This function validates the result received from the AI. Can be used for determine
     * if you need to prompt it again. Oceania is a special case, since a lot of the islands
     * in Oceania span across the "coordinate splitter" (coordinates goes from -180 to 180, Oceania has stuff
     * in-between longitude 111 and -161).
     *
     * @param promptResult the prompt result you want to validate
     * @return a boolean checker
     */
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
