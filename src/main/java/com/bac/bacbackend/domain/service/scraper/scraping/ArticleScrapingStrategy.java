package com.bac.bacbackend.domain.service.scraper.scraping;

import com.bac.bacbackend.domain.common.ContentAnalyzis;
import com.bac.bacbackend.domain.common.validators.SummaryValidator;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.*;

import java.util.ArrayList;

public class ArticleScrapingStrategy implements ScrapingStrategy {

    private final IWebSelectors webSelectors;
    private final SummaryValidator summaryValidator;
    private final ContentAnalyzis contentAnalyzis;

    public ArticleScrapingStrategy(IWebSelectors webSelectors, SummaryValidator summaryValidator, ContentAnalyzis contentAnalyzis) {
        this.webSelectors = webSelectors;
        this.summaryValidator = summaryValidator;
        this.contentAnalyzis = contentAnalyzis;
    }

    @Override
    public boolean doScrape(ScrapeContext scrapeContext) {

        scrapeContext.setTitle(webSelectors.txtValue(scrapeContext.getScrapingProperties().title()));
        scrapeContext.setSummary(webSelectors.txtValue(scrapeContext.getScrapingProperties().sum()));
        scrapeContext.setSummary(summaryValidator.summaryHandler(scrapeContext.getSummary()));

        System.out.println("title: "   + scrapeContext.getTitle());
        System.out.println("summary: " + scrapeContext.getSummary());

        ArrayList<String> analyzedContent = contentAnalyzis.analyzeContent(scrapeContext);
        if (analyzedContent != null && analyzedContent.size() == 8) {
            scrapeContext.setCity(analyzedContent.get(0));
            scrapeContext.setCountry(analyzedContent.get(1));
            scrapeContext.setContinent(analyzedContent.get(2));
            scrapeContext.setCategory(analyzedContent.get(3));
            scrapeContext.setX(analyzedContent.get(4));
            scrapeContext.setY(analyzedContent.get(5));
            scrapeContext.setSourceName(analyzedContent.get(6));
            scrapeContext.setImgUrl(analyzedContent.get(7));
            return true;
        }
        return false;
    }

}
