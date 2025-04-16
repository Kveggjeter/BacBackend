package com.bac.bacbackend.data.scraper.handling;

import org.openqa.selenium.WebDriver;

public interface Scrapable {
    void scrape(WebDriver driver, ScrapeSource source);
}
