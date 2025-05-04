package com.bac.bacbackend.domain.model.article;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * This data class gives context to what properties used for scraping and extracting information from the given
 * HTML and also used for saving the found information. Makes use of {@link ScrapingProperties}, which are sent into
 * the constructor as an argument each time a new context is made.
 */
public class ScrapeContext {
    private final ScrapingProperties scrapingProperties;
    private String articleUrl;
    private String imgUrl;
    private String title;
    private String summary;
    private String city;
    private String country;
    private String continent;
    private String category;
    private String x;
    private String y;
    private String sourceName;

    /**
     * Constructor used for creating a new object of ScrapeContext. "Merges" with both the {@link ArticleUrls} and
     * {@link ScrapingProperties} class, so it can be used for delivering and creating objects.
     *
     * @param articleUrls Data class containing urls of thumbnail images and the url that are to be scraped
     * @param scrapingProperties a set of instruction to the scraper of what elements to look for to extract given info
     */
    public ScrapeContext(ArticleUrls articleUrls, ScrapingProperties scrapingProperties) {
        this.articleUrl = articleUrls.articleUrl();
        this.imgUrl = articleUrls.imgUrl();
        this.scrapingProperties = scrapingProperties;
    }

    /**
     * Primarily used in {@link com.bac.bacbackend.domain.service.scraping.SaveScrapedArticle}. Gives life to the
     * {@link Article} record class that are used for representing an article in memory.
     *
     * @return a new domain Article based on info given
     */
    public Article toArticle() {
        return new Article(
                articleUrl, sourceName, title, summary,
                city, country, continent,
                category, x, y, imgUrl
        );
    }
}

