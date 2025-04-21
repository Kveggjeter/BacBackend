package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.common.Boundaries;
import com.bac.bacbackend.domain.common.Regex;
import com.bac.bacbackend.domain.common.StringResource;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.*;
import org.springframework.stereotype.Component;

@Component
public class Scraper extends ScraperSetup {

    private final IChrome browser;
    private final IWebSelectors cr;
    private final IOpenAi ai;
    private final String command = StringResource.COMMAND.getValue();
    private final Regex regex;
    private final INewsParamRepo nRepo;
    private final IFailedRepo fRepo;

    public Scraper(IArticleRepo repo, IChrome browser, IWebSelectors cr, IOpenAi ai, INewsParamRepo nRepo, Regex regex, IFailedRepo fRepo) {
        super(repo);
        this.browser = browser;
        this.cr = cr;
        this.ai = ai;
        this.nRepo = nRepo;
        this.regex = regex;
        this.fRepo = fRepo;
    }

    public void start(ArticleUrls a, int n) {
        ScrapeProps sp = nRepo.select(n);
        execute(a, sp);
    }

    @Override
    protected boolean doScrape(ScrapeContext scrapeContext) {
        Boundaries boundaries = new Boundaries();

        try {
            String threadName = getName();
            browser.start(scrapeContext.getUrl());

            if(interruptCheck(threadName)) {
                return false;
            }

            scrapeContext.setTitle(cr.txtValue(scrapeContext.getSp().title()));
            scrapeContext.setSummary(cr.txtValue(scrapeContext.getSp().sum()));
            scrapeContext.setSummary(sumHandler(scrapeContext.getSummary()));

            System.out.println("[" + threadName + "] title: "   + scrapeContext.getTitle());
            System.out.println("[" + threadName + "] summary: " + scrapeContext.getSummary());

            String[] res;
            int count = 0;
            boolean check;
            do {
                res = ai.prompt(command, scrapeContext.getTitle() + " " + scrapeContext.getSummary()).split("/");
                check = boundaries.coordinatesChecker(res);
                count++;
            }while(res.length != 6 || count > 3 || !check);

            if (res.length == 6) {
                scrapeContext.setCity(res[0]);
                scrapeContext.setCountry(res[1]);
                scrapeContext.setContinent(res[2]);
                scrapeContext.setCategory(res[3]);
                scrapeContext.setX(res[4]);
                scrapeContext.setY(res[5]);
            }
            else {
                System.err.println("[" + threadName + "] Prompt failed, AI returned " + res.length + " variables instead of prompted 6");
                return false;
            }

            scrapeContext.setSourceName(regex.urlName(scrapeContext.getUrl()));

            if (scrapeContext.getImgUrl() != null) {
                scrapeContext.setImgUrl(regex.imageSrc(scrapeContext.getImgUrl()));
            } else {
                scrapeContext.setImgUrl(cr.redo("img") != null ? regex.imageSrc(cr.redo("img")) : "NO_IMAGE");
            }


        } catch (Exception e) {
            System.err.println("[" + getName() + "] Failed to extract info from: " + scrapeContext.getUrl() + ": " + e.getMessage());
            fRepo.addToFail(scrapeContext.getUrl());
            throw e;
        } finally {
            try {
                browser.stop();
            } catch (Exception e) {
                System.err.println("[" + getName() + "] Failed to close browser: " + e.getMessage());
            }
        }
        return true;
    }



    private String sumHandler(String s) {
        if (s == null) {
            s = cr.txtValue("p");
            if (s == null) {
                System.err.println("[" + getName() + "] No summary found");
            }
        }  if (s.length() > 400) s = s.substring(0, 400) + "...";
        return s;
    }
}
