package com.bac.bacbackend.domain.model.scraper;

public record ScrapingProperties(
        String url,
        String txtLocator,
        String txtHref,
        String imgLocator,
        String imgHref,
        String title,
        String sum
) {}
