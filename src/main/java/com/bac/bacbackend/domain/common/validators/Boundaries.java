package com.bac.bacbackend.domain.common.validators;

/**
 * Class for controlling that the coordinates given by the LLM is correct. The class is to be created as
 * an instance, and not inherited.
 */
public class Boundaries {

    /**
     * Constructor for creating {@code Bondaries} as an object
     */
    public Boundaries() {}

    /**
     * Simple boolean checker that calls upon the enum of {@link CoordinateBoundaries}. This method could be static,
     * but remains non-static for the time being as there are plans to expand the usability of the class and that
     * its desirable to have it as an object where ever its used (together with the class). With the help of
     * {@link CoordinateBoundaries}, it makes a border that the given prompt has to keep inside. Oceania is a
     * special case, so it has its own If-statement. 
     *
     * @param promptResult the array containing the prompt result given from the AI
     * @return a boolean operator with the validity of the prompt
     */
    public boolean coordinatesChecker(String[] promptResult) {
        float x = Float.parseFloat(promptResult[4]);
        float y = Float.parseFloat(promptResult[5]);
        String country = promptResult[1].toLowerCase();
        String continent = (promptResult[2]).toLowerCase();

        CoordinateBoundaries coordinateBoundaries = CoordinateBoundaries.continentCheck(continent, country);
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
