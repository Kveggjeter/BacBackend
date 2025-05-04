package com.bac.bacbackend.data.repository.automation;

import com.bac.bacbackend.data.model.scraper.NewsParamEntity;
import com.bac.bacbackend.data.model.scraper.ScraperObjectEntity;
import com.bac.bacbackend.data.repository.scraper.NewsParamDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Repository used mainly for updating Redis with scraperproperties stored in postgresql.
 * They class can be used later for adding, but currently that interaction is mainly done outside of the
 * application.
 */
@Component
@RequiredArgsConstructor
public class ScraperObjectRepo {

    private final ScraperDataObjectRepo scrapeRepo;
    private final NewsParamDataRepo newRepo;

    /**
     * Protected method only to be used by {@link UpdateScraperProperties} in this package.
     * Merges the entity classes and adds the prepared property to Redis from postgres.
     * @See package.com.bac.bacbackend.data.repository.automation
     */
    protected void cache() {
        List<ScraperObjectEntity> soe = (List<ScraperObjectEntity>) scrapeRepo.findAll();
        for (ScraperObjectEntity en : soe) {
            newRepo.save(addSome(en));
        }

        System.out.println("Cache updated with: " + soe.size() + " objects");
    }

    /**
     * Helper method for setting the NewsParamEntity class later used for adding properties to Redis
     * @param so the scraper object representing the data from postgres
     * @return the redis-friendly entity
     */
    private NewsParamEntity addSome(ScraperObjectEntity so) {
        NewsParamEntity ns = new NewsParamEntity();
        ns.setUrl(so.getUrl());
        ns.setTxtLocator(so.getTxtLocator());
        ns.setTxtHref(so.getTxtHref());
        ns.setImgLocator(so.getImgLocator());
        ns.setImgHref(so.getImgHref());
        ns.setTitle(so.getTitle());
        ns.setSum(so.getSum());
        return ns;
    }

}
