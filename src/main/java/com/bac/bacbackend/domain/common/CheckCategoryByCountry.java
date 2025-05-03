package com.bac.bacbackend.domain.common;

import java.util.HashMap;

public class CheckCategoryByCountry {

    private final HashMap<String, Integer> categoryCount;

    public CheckCategoryByCountry(HashMap<String, Integer> categoryCount) {
        this.categoryCount = categoryCount;
        categoryCount.put("business", 0);
        categoryCount.put("crime", 0);
        categoryCount.put("culture", 0);
        categoryCount.put("politics", 0);
        categoryCount.put("science", 0);
        categoryCount.put("sports", 0);
    }

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
