package com.bac.bacbackend.domain.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "scraper")
public class ScraperProps {
    @Value("${scraper.chromedriverPath}")
    private String chromedriverPath;
    private boolean headless = true;
    @Value("${scraper.userAgent}")
    private String userAgent;
}
