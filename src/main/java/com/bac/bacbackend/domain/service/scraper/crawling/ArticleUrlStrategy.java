package com.bac.bacbackend.domain.service.scraper.crawling;

import com.bac.bacbackend.domain.common.validators.ArticleUrlValidator;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IWebSelectors;

import java.util.ArrayList;
import java.util.List;

public class ArticleUrlStrategy implements CrawlingStrategy<ArticleUrls> {

    private final IWebSelectors webSelectors;
    private final ArticleUrlValidator articleUrlValidator;

    public ArticleUrlStrategy(IWebSelectors webSelectors, ArticleUrlValidator articleUrlValidator){
        this.webSelectors = webSelectors;
        this.articleUrlValidator = articleUrlValidator;
    }

    @Override
    public List<ArticleUrls> extractUrls(ScrapingProperties scrapingProperties, int maxUrlsToCrawl) {
        List<ArticleUrls> listOfUrlsToBeScraped = new ArrayList<>();
        List<String> articleUrls = webSelectors.values(scrapingProperties.txtLocator());
        List<String> imgUrls = webSelectors.values(scrapingProperties.imgLocator(),
                scrapingProperties.imgHref());

        if (maxUrlsToCrawl > articleUrls.size()) maxUrlsToCrawl = articleUrls.size();
        int maxListSize = Math.min(articleUrls.size(), imgUrls.size());


        int countOfArticlesAdded = 0;
        int i = 0;
        while (countOfArticlesAdded < maxUrlsToCrawl && i < maxListSize) {
            String articleUrl = articleUrls.get(i);
            String imgUrl = imgUrls.get(i);
            boolean ok = articleUrlValidator.isArticleAlreadyAdded(articleUrl);
            if (ok && !listOfUrlsToBeScraped.contains(new ArticleUrls(articleUrl, imgUrl))) {
                countOfArticlesAdded++;
                listOfUrlsToBeScraped.add(new ArticleUrls(articleUrl, imgUrl));
            }
            i++;
        }
        return listOfUrlsToBeScraped;
    }
}
