package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.IArticleRepo;

public abstract class Decomp {

    private final IArticleRepo repo;

    protected Decomp(IArticleRepo repo) { this.repo = repo;}

    protected final void execute(ArticleData data, ScrapeProps sp) {
        ScrapeContext sc = new ScrapeContext(data, sp);
        if(doScrape(sc))
        save(sc);
        else System.out.println("Error");
    }

    protected abstract boolean doScrape(ScrapeContext sc);

    protected String getName() { return Thread.currentThread().getName(); }

    protected boolean interruptCheck(String s) {
        if(Thread.currentThread().isInterrupted()) {
            System.err.println("[" + s + "] failed fetching");
            return true;
        }
        return false;
    }

    private void save(ScrapeContext sc) {
        try {
            repo.adder(sc.toArticle());
            System.out.println("[" + getName() + "] Saved: "
                    + sc.getUrl());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected issue: " + e.getMessage(), e);
        }
    }

}

