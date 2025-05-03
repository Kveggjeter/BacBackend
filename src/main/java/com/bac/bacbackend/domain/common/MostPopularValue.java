package com.bac.bacbackend.domain.common;

import java.util.HashMap;

public class MostPopularValue {

    private final HashMap<String, Integer> mapOfValues;
    private int mostFrequentValue = 0;
    private String keyWithHighestValue;

    public MostPopularValue() {
        this.mapOfValues = new HashMap<>();
    }

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

    public String getKeyWithHighestValue() {
        return keyWithHighestValue;
    }


}
