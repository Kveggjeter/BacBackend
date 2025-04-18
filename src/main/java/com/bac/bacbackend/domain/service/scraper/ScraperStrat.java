package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.common.Regex;
import com.bac.bacbackend.domain.port.*;
import org.springframework.stereotype.Component;

@Component
public class ScraperStrat extends Decomp {

    private final IChrome browser;
    private final ICrawler cr;
    private final IOpenAi ai;
    private final String command = StringResource.COMMAND.getValue();
    private final Regex regex;
    private final INewsParamRepo nRepo;


    public ScraperStrat(IArticleRepo repo, IChrome browser, ICrawler cr, IOpenAi ai, INewsParamRepo nRepo, Regex regex) {
        super(repo);
        this.browser = browser;
        this.cr = cr;
        this.ai = ai;
        this.nRepo = nRepo;
        this.regex = regex;
    }

    public void start(String s) {
        this.url = s;
        setSp(nRepo.select(5));
        scrape();
    }

    private void scrape() {

        try {
            String threadName = getName();
            browser.start(url);

            if(interruptCheck(threadName)) {
                browser.stop();
                return;
            }

            title = cr.value(sp.title());
            summary = cr.value(sp.sum());
            sumNullCheck();
            sumCut();
            loggers(1);

            String[] res = ai.prompt(command, title + " " + summary).split("/");
            if(aiFaultCheck(res.length)) return;
            promptHandler(res);

            sourceName = regex.urlName(url);
            if (imgUrl != null) {
                imgUrl = regex.imageSrc(imgUrl);
                imgNullCheck();
            } else imgUrl = "NO_IMAGE";

            setArticle();
            loggers(2);
            browser.stop();

        } catch (Exception e) {
            System.err.println("[" + getName() + "] Failed to extract info from: " + url + ": " + e.getMessage());
            browser.stop();
            throw new RuntimeException(e);
        }
    }

    private void imgNullCheck() {
        if (imgUrl == null) {
            String resImg = cr.redo("img");
            imgUrl = regex.imageSrc(resImg);
        }
    }

    private void promptHandler(String[] res) {
            city = res[0];
            country = res[1];
            continent = res[2];
            category = res[3];
            x = res[4];
            y = res[5];
    }

    private boolean aiFaultCheck(int n) {
        if(!(n == 6)) {
            System.err.println("[" + getName() + "] " + "Prompt gone wrong, expected 6 output but only got: " + n);
            System.err.println(ai.prompt(command, summary));
            return true;
        }
        return false;
    }

    private String getName() {
        System.out.println("[" + Thread.currentThread().getName() + "] Fetching from " + url);
        return Thread.currentThread().getName();
    }

    private boolean interruptCheck(String s) {
        if(Thread.currentThread().isInterrupted()) {
            System.err.println("[" + s + "] failed fetching from " + url);
            browser.stop();
            return true;
        }
        return false;
    }

    private void sumNullCheck() {
        if (summary == null) {
            summary = cr.value("p");
            if (summary == null) {
                System.err.println("[" + getName() + "] No summary found");
            }
        }
    }

    private void sumCut() {
        if (summary.length() > 400) summary = summary.substring(0, 400) + "...";
    }

    private void loggers(int n) {
        switch (n) {
            case 1:
                System.out.println("summary: " + summary);
                System.out.println("title: " + title);
                break;
                case 2:
                    System.out.println("[" + getName() + "] Saving the article in db with the ID: " + url);
                    break;
                    default:
                        break;
        }
    }
}
