package com.bac.bacbackend.domain.service.crawling;

import com.bac.bacbackend.domain.common.validators.ArticleUrlValidator;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IWebSelectors;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the crawling strategy.This strategy focuses on collecting a list of all available
 * articles at the given webpage. The urls are qualified by checking if it exists from before.
 */
@RequiredArgsConstructor
public class ArticleUrlStrategy implements CrawlingStrategy<ArticleUrls> {

    private final IWebSelectors webSelectors;
    private final ArticleUrlValidator articleUrlValidator;

    /**
     * This method is used for extracting urls from a webpage. It collects both the thumbnail image (imgUlrs) and the
     * given article url. The url is then scraped with a scraper and the thumbnail image is later added to the same
     * data object (as it's sent through a list containing these data classes).
     * <p>
     * Since we have ways to qualify which article is to be sent to the scraper, we also have some measures to make
     * sure we don't run out of bounds. Sometimes there might be more images than urls (or visa-verca), so we stop
     * the iteration for both max urls and the smallest value of the available images and article urls.
     *
     * @param scrapingProperties properties for each webpage with webelements to watch for
     * @param maxUrlsToCrawl the max amount of urls to fetch
     * @return a list of urls to be scraped
     */
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
            System.out.println("Count:" + countOfArticlesAdded + " out of " + maxUrlsToCrawl + "." );
            String imgUrl = imgUrls.get(i);
            boolean ok = articleUrlValidator.isArticleAlreadyAdded(articleUrl);
            if (ok && !listOfUrlsToBeScraped.contains(new ArticleUrls(articleUrl, imgUrl))) {
                countOfArticlesAdded++;
                listOfUrlsToBeScraped.add(new ArticleUrls(articleUrl, imgUrl));
                System.out.println("Added " + articleUrl + " and this image." + imgUrl + ".");
            }
            i++;
        }
        return listOfUrlsToBeScraped;
    }
}
