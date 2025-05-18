package com.bac.bacbackend.domain.service.crawling;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IBrowser;
import java.util.List;

/**
 * Generic class for all crawlers. The data class passed in as the dependent data object must also be the
 * object used as the type in the list.
 * @param <T> The dataclass used for saving articles. Change out with preferred data class
 */
public class Crawler<T> {
    private final IBrowser browser;
    private final CrawlingStrategy<T> crawlingStrategy;

    public Crawler(IBrowser browser, CrawlingStrategy<T> crawlingStrategy) {
        this.browser = browser;
        this.crawlingStrategy = crawlingStrategy;
    }

    /**
     * Generic method used by all crawlers, all crawler need a max amount of urls to crawl and
     * info about which parameters and webelements that valid. This instance also holds the extension of the browser
     * instance, and will take care of ending it properly. This is also the reason it has to be used by all other
     * crawlers, so we can be certain the browser is correctly killed off.
     *
     * @param scrapingProperties properties for each webpage with webelements to watch for
     * @param maxUrlsToCrawl the max amount of urls to fetch
     * @return a generic interpretation for the list of data objects containing further scraping materials
     */
    public List<T> crawl(ScrapingProperties scrapingProperties, int maxUrlsToCrawl) {
        try {
            browser.start(scrapingProperties.url());
            System.out.println("Crawling webpage, please wait: " + scrapingProperties.url());
            return crawlingStrategy.extractUrls(scrapingProperties, maxUrlsToCrawl);
        } finally {
            browser.stop();
            System.out.println("Crawling finished, sending to scraper: " + scrapingProperties.url());
        }
    }
}
