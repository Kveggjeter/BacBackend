package com.bac.bacbackend.data.scraper.handling;

import java.util.List;

public interface ScrapeSource <T> {
    List<T> getUrl();
    List<T> getTxtLocator();
    List<T> getTxtHref();
    List<T> getImgLocator();
    List<T> getImgHref();
    List<T> getTitle();
    List<T> getSum();
}
