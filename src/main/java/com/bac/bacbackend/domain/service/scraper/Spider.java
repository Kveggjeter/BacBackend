package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.ICrawler;

import java.util.ArrayList;
import java.util.List;

public abstract class Spider <T> {

    protected final IChrome browser;
    protected final ICrawler cr;

    protected String startUrl;
    protected List<T> list = new ArrayList<>();

    protected Spider(IChrome browser, ICrawler cr) {
        this.browser = browser;
        this.cr = cr;
    }

    private void reset() {
            list.clear();
    }

    protected abstract void propsSetter(int n);

    protected abstract void startCrawling(int max);

    protected List<T> doCrawl(int n, int max) {
        reset();
        propsSetter(n);
        browser.start(startUrl);

        try {
            System.out.println("Crawling webpage, please wait...");
            startCrawling(max);
        } finally {
            browser.stop();
        }
        System.out.println("Crawling finished, sending to scraper..");
        return list;
    }

}
