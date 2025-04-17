package com.bac.bacbackend.domain.model;

public record SourceDto(
        String url,
        String txtLocator,
        String txtHref,
        String imgLocator,
        String imgHref,
        String title,
        String sum
) {}
