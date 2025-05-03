package com.bac.bacbackend.domain.model.article;

import com.bac.bacbackend.domain.common.CheckCategoryByCountry;
import com.bac.bacbackend.domain.common.MostPopularValue;
import java.util.HashMap;

public record ArticleFacts(
    int articleCount,
    int businessCategoryCount,
    int crimeCategoryCount,
    int cultureCategoryCount,
    int politicsCategoryCount,
    int scienceCategoryCount,
    int sportsCategoryCount,
    String cityWithMostCoverage,
    String mostFrequentNewsSource
){
    public static ArticleFacts getArticleFacts(String country, Iterable<Article> articles) {
        int articleCount = 0;
        HashMap<String, Integer> categoryCount = new HashMap<>();
        CheckCategoryByCountry checkCategoryByCountry = new CheckCategoryByCountry(categoryCount);
        MostPopularValue mostPopularCity = new MostPopularValue();
        MostPopularValue mostPopularSource = new MostPopularValue();

        for (Article article : articles) {
            if (article != null
                    && article.country() != null
                    && article.country().equalsIgnoreCase(country)) {
                checkCategoryByCountry.countCategories(article.category().toLowerCase());
                mostPopularCity.checkForMostPopularValue(article.city().toLowerCase());
                mostPopularSource.checkForMostPopularValue(article.sourceName().toLowerCase());
                articleCount++;
            }
        }
        return new ArticleFacts(
                articleCount, categoryCount.get("business"),
                categoryCount.get("crime"), categoryCount.get("culture"),
                categoryCount.get("politics"), categoryCount.get("science"),
                categoryCount.get("sports"), mostPopularCity.getKeyWithHighestValue(),
                mostPopularSource.getKeyWithHighestValue()
        );
    }
}
