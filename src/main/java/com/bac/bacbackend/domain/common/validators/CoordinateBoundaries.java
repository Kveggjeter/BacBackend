package com.bac.bacbackend.domain.common.validators;

/**
 * Static boundaries for coordinates, not meant to change.
 * The object is meant to be created as it is an enum, but will probably end up as static down the line.
 */
public enum CoordinateBoundaries {
    EUROPE (80f, 33f, 55f, 29f),
    ASIA (82f, -10f,  171f, 26f),
    OCEANIA (14f, -67f, 180f,  111f,  -161f,  -180f),
    AFRICA (37f, -37f,  56f,  -26f),
    SOUTH_AMERICA (31f,  -56f, -34f,  -91f),
    NORTH_AMERICA (84f,   7f, -52f, -167f),
    AUSTRALIA (-9f, -44f, 155f,  112f);

    private final float maxLat;
    private final float minLat;
    private final float maxLonOne;
    private final float minLonOne;
    private final Float maxLonTwo;
    private final Float minLonTwo;

    /**
     * Constructor class used for normal instances.
     *
     * @param maxLat maximum allowed latitude
     * @param minLat minimum allowed latitude
     * @param maxLonOne maximum allowed longitude
     * @param minLonOne minimum allowed longitude
     */
    CoordinateBoundaries(float maxLat, float minLat, float maxLonOne, float minLonOne){
        this(maxLat, minLat, maxLonOne, minLonOne, null, null);
    }

    /**
     * Overfitted constructor meant to be used sole by Oceania, as it's borders are crossing the +- border
     * in the pacific.
     *
     * @param maxLat maximum allowed latitude
     * @param minLat minimum allowed latitude
     * @param maxLonOne maximum allowed longitude
     * @param minLonOne minimum allowed longitude
     * @param maxLonTwo maximum allowed latitude in extension
     * @param minLonTwo minimum allowed latitude in extension
     */
    CoordinateBoundaries(float maxLat, float minLat, float maxLonOne, float minLonOne,
    Float maxLonTwo, Float minLonTwo) {
        this.maxLat = maxLat;
        this.minLat = minLat;
        this.maxLonOne = maxLonOne;
        this.minLonOne = minLonOne;
        this.maxLonTwo = maxLonTwo;
        this.minLonTwo = minLonTwo;
    }

    /**
     * @return maximum allowed latitude
     */
    public float getMaxLat() { return maxLat; }

    /**
     * @return minimum allowed latitude
     */
    public float getMinLat() { return minLat; }

    /**
     * @return maximum allowed longitude
     */
    public float getMaxLonOne() { return maxLonOne; }

    /**
     * @return minimum allowed longitude
     */
    public float getMinLonOne() { return minLonOne; }

    /**
     * @return maximum allowed latitude in extension
     */
    public Float getMaxLonTwo() { return maxLonTwo; }

    /**
     * @return minimum allowed latitude in extension
     */
    public Float getMinLonTwo() { return minLonTwo; }

    /**
     * Static switch case that returns the boundaries that are valid for the given continents(country).
     * Fallbacks to null in case something wierd happens, if the value is null the whole check is cancelled and
     * the coordinate are accepted. It happens rarely, and has to be like that in case the LLM decides to go rouge
     * and say the continent is "Antarctica" or "Mariana Trench" (it has happened, quite  a lot unfortunately)
     *
     * @param continent continent that are to be checked
     * @param country country, in case of Australia since Oceania is a special case
     * @return returns what coordinates are to be checked for
     */
    public static CoordinateBoundaries continentCheck(String continent, String country) {

        switch (continent) {
            case "australia", "oceania" -> {
                if (country.equals("australia")) return AUSTRALIA;
                return OCEANIA;
            }
            case "north america", "north-america", "america", "united states", "united-states", "usa" -> {
                return NORTH_AMERICA;
            }
            case "south america", "south-america", "latin america", "latin-america" -> {
                return SOUTH_AMERICA;
            }
            case "middle-east", "middle east", "asia" -> {
                return ASIA;
            }
            case "europa" -> {
                return EUROPE;
            }
            case "africa" -> {
                return AFRICA;
            }
            default -> {
                return null;
            }
        }
    }
}
