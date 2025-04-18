package com.bac.bacbackend.domain.model.scraper;

public record ScrapeProps(
        String url,
        String txtLocator,
        String txtHref,
        String imgLocator,
        String imgHref,
        String title,
        String sum
) {}
