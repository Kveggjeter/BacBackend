package com.bac.bacbackend.domain.service.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IArticleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class for saving the scraped article. Spring does a lot of the magic, so this class is simply calling
 * the {@link IArticleRepo} interface
 */
@Component
public class SaveScrapedArticle {

    private final IArticleRepo repository;

    public SaveScrapedArticle(IArticleRepo repository) {
        this.repository = repository;
    }

    /**
     * Simply echo-method for saving
     *
     * @param scrapeContext context used for scraping, also the data object that contains the scraped information
     */
    public void save(ScrapeContext scrapeContext) {
        repository.adder(scrapeContext.toArticle());
    }
}
