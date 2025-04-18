package com.bac.bacbackend.domain.port;

import com.bac.bacbackend.domain.model.scraper.ScrapeProps;

public interface INewsParamRepo {
    ScrapeProps select(int n);
    int getCount();
}
