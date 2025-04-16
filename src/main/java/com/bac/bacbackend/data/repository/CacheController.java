package com.bac.bacbackend.data.repository;

import com.bac.bacbackend.domain.model.NewSource;
import com.bac.bacbackend.domain.model.ScraperObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CacheController {

    private final ScraperObjectRepository scrapRepo;
    private final NewSourceRepository newRepo;

    public CacheController (ScraperObjectRepository scrapRepo, NewSourceRepository newRepo) {
        this.scrapRepo = scrapRepo;
        this.newRepo = newRepo;
    }

    /**
     * Is it necessary with Transactional? It's been here since the problem with
     * caching from postgres and I've just been to afraid to move it
     * TODO: (re)move?
     */
    @Transactional
    public void cache() {
        List<ScraperObject> scraperObjects = (List<ScraperObject>) scrapRepo.findAll();
        for (ScraperObject scraperObject : scraperObjects) {
            NewSource newSource = convertToNewSource(scraperObject);
            newRepo.save(newSource);
        }

        System.out.println("Cache oppdatert. Antall objekter: " + scraperObjects.size());
    }

    private NewSource convertToNewSource(ScraperObject scraperObject) {
        NewSource newSource = new NewSource();
        newSource.setUrl(scraperObject.getUrl());
        newSource.setTxtLocator(scraperObject.getTxtLocator());
        newSource.setTxtHref(scraperObject.getTxtHref());
        newSource.setImgLocator(scraperObject.getImgLocator());
        newSource.setImgHref(scraperObject.getImgHref());
        newSource.setTitle(scraperObject.getTitle());
        newSource.setSum(scraperObject.getSum());
        return newSource;
    }
}
