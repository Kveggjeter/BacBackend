package com.bac.bacbackend.domain.common;

public enum CoordinateBoundaries {
    EUROPE (80f, 33f, 55f, 29f),
    ASIA (82f, -10f,  26f, -171f),
    OCEANIA (14f, -67f, 180f,  111f,  -161f,  -180f),
    AFRICA (37f, -37f,  56f,  -26f),
    SOUTH_AMERICA (14f,  -7f, -66f,  -77f),
    NORTH_AMERICA (84f,   7f, -52f, -167f),
    AUSTRALIA (-9f, -44f, 155f,  112f);

    private final float maxLat;
    private final float minLat;
    private final float maxLonOne;
    private final float minLonOne;
    private final Float maxLonTwo;
    private final Float minLonTwo;

    CoordinateBoundaries(float maxLat, float minLat, float maxLonOne, float minLonOne){
        this(maxLat, minLat, maxLonOne, minLonOne, null, null);
    }

    CoordinateBoundaries(float maxLat, float minLat, float maxLonOne, float minLonOne,
    Float maxLonTwo, Float minLonTwo) {
        this.maxLat = maxLat;
        this.minLat = minLat;
        this.maxLonOne = maxLonOne;
        this.minLonOne = minLonOne;
        this.maxLonTwo = maxLonTwo;
        this.minLonTwo = minLonTwo;
    }

    public float getMaxLat() { return maxLat; }
    public float getMinLat() { return minLat; }
    public float getMaxLonOne() { return maxLonOne; }
    public float getMinLonOne() { return minLonOne; }
    public Float getMaxLonTwo() { return maxLonTwo; }
    public Float getMinLonTwo() { return minLonTwo; }

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
