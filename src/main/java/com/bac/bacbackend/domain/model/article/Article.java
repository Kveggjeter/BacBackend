package com.bac.bacbackend.domain.model.article;

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
