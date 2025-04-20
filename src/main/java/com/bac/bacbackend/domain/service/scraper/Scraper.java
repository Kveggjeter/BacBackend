package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.common.Regex;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.*;
import org.springframework.stereotype.Component;

@Component
public class Scraper extends Decomp {

    private final IChrome browser;
    private final ICrawler cr;
    private final IOpenAi ai;
    private final String command = StringResource.COMMAND.getValue();
    private final Regex regex;
    private final INewsParamRepo nRepo;

    public Scraper(IArticleRepo repo, IChrome browser, ICrawler cr, IOpenAi ai, INewsParamRepo nRepo, Regex regex) {
        super(repo);
        this.browser = browser;
        this.cr = cr;
        this.ai = ai;
        this.nRepo = nRepo;
        this.regex = regex;
    }

    public void start(ArticleData a, int n) {
        ScrapeProps sp = nRepo.select(n);
        execute(a, sp);
    }

    @Override
    protected boolean doScrape(ScrapeContext sc) {

        try {
            String threadName = getName();
            browser.start(sc.getUrl());

            if(interruptCheck(threadName)) {
                return false;
            }

            sc.setTitle(cr.txtValue(sc.getSp().title()));
            sc.setSummary(cr.txtValue(sc.getSp().sum()));
            sc.setSummary(sumHandler(sc.getSummary()));

            System.out.println("[" + threadName + "] title: "   + sc.getTitle());
            System.out.println("[" + threadName + "] summary: " + sc.getSummary());

            String[] res;
            int count = 0;
            do {
                res = ai.prompt(command, sc.getTitle() + " " + sc.getSummary()).split("/");
                count++;
            }while(res.length != 6 || count > 3);

            if (res.length == 6) {
                sc.setCity(res[0]);
                sc.setCountry(res[1]);
                sc.setContinent(res[2]);
                sc.setCategory(res[3]);
                sc.setX(res[4]);
                sc.setY(res[5]);
            }
            else {
                System.err.println("[" + threadName + "] Prompt failed, AI returned " + res.length + " variables instead of prompted 6");
                return false;
            }

            sc.setSourceName(regex.urlName(sc.getUrl()));

            if (sc.getImgUrl() != null) {
                sc.setImgUrl(regex.imageSrc(sc.getImgUrl()));
            } else {
                sc.setImgUrl(cr.redo("img") != null ? regex.imageSrc(cr.redo("img")) : "NO_IMAGE");
            }


        } catch (Exception e) {
            System.err.println("[" + getName() + "] Failed to extract info from: " + sc.getUrl() + ": " + e.getMessage());
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
