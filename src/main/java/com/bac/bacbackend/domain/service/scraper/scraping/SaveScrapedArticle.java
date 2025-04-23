package com.bac.bacbackend.domain.service.scraper.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IArticleRepo;
import org.springframework.stereotype.Component;

@Component
public class SaveScrapedArticle {

    private final IArticleRepo repository;

    public SaveScrapedArticle(IArticleRepo repository) {
        this.repository = repository;
    }

    public void save(ScrapeContext scrapeContext) {
        repository.adder(scrapeContext.toArticle());
    }
}
