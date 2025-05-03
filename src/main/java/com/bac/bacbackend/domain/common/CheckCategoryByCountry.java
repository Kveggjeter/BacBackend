package com.bac.bacbackend.domain.common;

import java.util.HashMap;

/**
 * Class for handling the counting of what category an article belong to. Used primarily by country (as the class name hints)
 */
public class CheckCategoryByCountry {

    private final HashMap<String, Integer> categoryCount;

    /**
     * Constructor gives base values to the inputted HashMap. Handles the hashmap as an instance in memory here.
     *
     * @param categoryCount a HashMap instanced somewhere else
     */
    public CheckCategoryByCountry(HashMap<String, Integer> categoryCount) {
        this.categoryCount = categoryCount;
        categoryCount.put("business", 0);
        categoryCount.put("crime", 0);
        categoryCount.put("culture", 0);
        categoryCount.put("politics", 0);
        categoryCount.put("science", 0);
        categoryCount.put("sports", 0);
    }

    /**
     * Switch case that adds value to the categories each time they appear.
     * @param article Single article as string, checks for which category it belongs
     */
    public void countCategories(String article) {

        switch (article) {
            case "business" -> {
                categoryCount.put("business", categoryCount.get("business") + 1);
            }
            case "crime" -> {
                categoryCount.put("crime", categoryCount.get("crime") + 1);
            }
            case "culture" -> {
                categoryCount.put("culture", categoryCount.get("culture") + 1);
            }
            case "politics" -> {
                categoryCount.put("politics", categoryCount.get("politics") + 1);
            }
            case "science" -> {
                categoryCount.put("science", categoryCount.get("science") + 1);
            }
            case "sports" -> {
                categoryCount.put("sports", categoryCount.get("sports") + 1);
            }
            default -> System.out.println("No category found");
        }
    }

}
