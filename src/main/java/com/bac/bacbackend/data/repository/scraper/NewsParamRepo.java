package com.bac.bacbackend.data.repository.scraper;

import com.bac.bacbackend.data.model.scraper.NewsParamEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import org.springframework.stereotype.Component;

/**
 * Brigde between websource and the scraper functionality. Responsible for getting the correct
 * properties for crawling.
 */
@Component
public class NewsParamRepo extends DataSourceHandler<NewsParamEntity, String> implements
        INewsParamRepo {

    public NewsParamRepo(NewsParamDataRepo repo) {
        super(repo);
    }

    /**
     * Selecting which webelement to scrape with, returns a domain-friendly property-class
     *
     * @param propertyIndex choosing which web element to scrape with
     * @return returns the scraper properties, readable from the domain class
     */
    @Override
    public ScrapingProperties select(int propertyIndex) {
        NewsParamEntity entity = get(propertyIndex);
        return toDomain(entity);
    }

    /**
     * Converts entity class to domain class
     *
     * @param entity the entity for the news-parameters
     * @return scraper properties usable to the domain
     */
    private ScrapingProperties toDomain(NewsParamEntity entity) {
        return new ScrapingProperties(
                entity.getUrl(),
                entity.getTxtLocator(),
                entity.getTxtHref(),
                entity.getImgLocator(),
                entity.getImgHref(),
                entity.getTitle(),
                entity.getSum()
        );
    }

    /**
     * Gets the total amount of news sources, important to use this for every crawling to
     * not get a indexOutOfBounds exception.
     *
     * @return size of the list containing news sources
     */
    @Override
    public int sumOfAllSources() {
        return size();
   }

}
