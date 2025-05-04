package com.bac.bacbackend.domain.common;

import java.util.HashMap;

/**
 * A common utility class made for determining and keeping track of the most frequent value in a HashMap
 * The class is meant to be instantiated everytime it is used, since the tracking happens in memory and must not
 * be interfered with.
 */
public class MostPopularValue {

    private final HashMap<String, Integer> mapOfValues;
    private int mostFrequentValue = 0;
    private String keyWithHighestValue;

    /**
     * Instantiates the Hashmap when ever the class is constructed
     */
    public MostPopularValue() {
        this.mapOfValues = new HashMap<>();
    }

    /**
     * Simple method that checks if the value exist from before, if not it gets a brand-new entry. If it exists,
     * we add +1 to it to indicate its just another of the same value. The value then get checked against
     * the global variable {@code mostFrequentValue} that holds the current highest number. If its over that, it takes
     * its place.
     *
     * @param article a single article as a string, containing a value that wants to be checked
     */
    public void checkForMostPopularValue(String article){
        if(mapOfValues.containsKey(article)){
            mapOfValues.put(article, mapOfValues.get(article)+1);
        } else {
            mapOfValues.put(article, 1);

        }
        if (mapOfValues.get(article)> mostFrequentValue) {
            mostFrequentValue = mapOfValues.get(article);
            keyWithHighestValue = article;
        }
    }

    /**
     * Method that is called after the iteration is done with {@code checkForMostPopularValue}.
     * @return the key that holds the highest number
     */
    public String getKeyWithHighestValue() {
        return keyWithHighestValue;
    }


}
