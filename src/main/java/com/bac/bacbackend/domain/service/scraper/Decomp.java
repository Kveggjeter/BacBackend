package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.IArticleRepo;

public abstract class Decomp {

    private final IArticleRepo repo;

    protected Decomp(IArticleRepo repo) { this.repo = repo;}

    public final void execute(ArticleData data, ScrapeProps sp) {
        ScrapeContext sc = new ScrapeContext(data, sp);
        doScrape(sc);
        save(sc);
    }

    protected abstract void doScrape(ScrapeContext sc);

    private void save(ScrapeContext sc) {
        try {
            repo.adder(sc.toArticle());
            System.out.println("[" + Thread.currentThread().getName() + "] Saved: "
                    + sc.getUrl());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected issue: " + e.getMessage(), e);
        }
    }

}

