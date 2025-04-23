package com.bac.bacbackend.domain.port;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;

public interface INewsParamRepo {
    ScrapingProperties select(int n);
    int sumOfAllSources();
}
