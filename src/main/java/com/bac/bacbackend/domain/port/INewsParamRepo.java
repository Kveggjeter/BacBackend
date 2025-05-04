package com.bac.bacbackend.domain.port;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;

/**
 * Interface for the news repository
 */
public interface INewsParamRepo {
    ScrapingProperties select(int n);
    int sumOfAllSources();
}
