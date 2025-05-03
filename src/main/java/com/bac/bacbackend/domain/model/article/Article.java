package com.bac.bacbackend.domain.model.article;

/**
 * Data class for the article, used for representing it in the domain layer
 * @param id url
 * @param sourceName name of the newssource
 * @param title of the article
 * @param summary of the article
 * @param city of where the article happens
 * @param country of where the article happens
 * @param continent of where the article happens
 * @param category that the article belongs to
 * @param x the latitude of where the article is georeferance to
 * @param y the longitude of where the article is georeferance to
 * @param imgUrl the thumbnail image
 */
public record Article(
        String id,
        String sourceName,
        String title,
        String summary,
        String city,
        String country,
        String continent,
        String category,
        String x,
        String y,
        String imgUrl
) {}
