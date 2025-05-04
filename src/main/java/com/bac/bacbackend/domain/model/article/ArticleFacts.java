package com.bac.bacbackend.domain.model.article;

import com.bac.bacbackend.domain.common.CheckCategoryByCountry;
import com.bac.bacbackend.domain.common.MostPopularValue;
import java.util.HashMap;

/**
 * This is both a data class and a querying class for getting some statistics to the frontend web-application.
 * As record class, its function is to either contain data when accessed in the applications memory or to use as a
 * brigade to other layers of the application. This does exactly that, and there would be redundant to split the class
 * up further. By having it centralized here makes it easier to add values we want to fetch, for example
 * "most popular category".
 *
 * @param articleCount count of how many articles
 * @param businessCategoryCount count of how many articles are in the business category
 * @param crimeCategoryCount count of how many articles are in the crime category
 * @param cultureCategoryCount count of how many articles are in the culture category
 * @param politicsCategoryCount count of how many articles are in the politics category
 * @param scienceCategoryCount count of how many articles are in the science category
 * @param sportsCategoryCount count of how many articles are in the sports category
 * @param cityWithMostCoverage the city that has the most cover based on the inputted country
 * @param mostFrequentNewsSource the news source that has place the most articles in the respected country
 */
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
    /**
     * Static method for handling queries to Redis. Makes use of two supporter classes,  {@link CheckCategoryByCountry}
     * and {@link MostPopularValue}. It creates their own separate instances, as they have mutable values we don't want
     * to mute by accident. We iterate through all instances in O(n) since it's stored in Redis. We then return
     * a new data class containing all the found information.
     *
     * @param country the country queried
     * @param articles an Iterable object of all the articles
     * @return a data object of ArticleFacts containing information about the queried country.
     */
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
