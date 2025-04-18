package com.bac.bacbackend.domain.model.browser;

public record ChromeDto(
        String driverpath,
        boolean headless,
        String alias
) {}
