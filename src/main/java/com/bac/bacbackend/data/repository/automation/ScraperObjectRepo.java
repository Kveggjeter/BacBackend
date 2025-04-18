package com.bac.bacbackend.data.repository.automation;

import com.bac.bacbackend.data.model.scraper.NewsParamEntity;
import com.bac.bacbackend.data.model.scraper.ScraperObjectEntity;
import com.bac.bacbackend.data.repository.scraper.NewsParamDataRepo;
import com.bac.bacbackend.domain.port.IScraperObjectRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScraperObjectRepo implements IScraperObjectRepo {

    private final ScraperDataObjectRepo scrapeRepo;
    private final NewsParamDataRepo newRepo;

    public ScraperObjectRepo(ScraperDataObjectRepo ScrapeRepo, NewsParamDataRepo newRepo) {
        this.scrapeRepo = ScrapeRepo;
        this.newRepo = newRepo;
    }

    public void cache() {
        merge();
    }

    private void merge() {
        List<ScraperObjectEntity> soe = (List<ScraperObjectEntity>) scrapeRepo.findAll();
        for (ScraperObjectEntity en : soe) {
            newRepo.save(addSome(en));
        }

        System.out.println("Cache oppdatert. Antall objekter: " + soe.size());
    }

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
