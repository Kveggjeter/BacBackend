package com.bac.bacbackend.datasource;

import java.util.ArrayList;

public class CrawlerMain {
    public static void main(String[] args) {
        ArrayList<WebCrawler> bots = new ArrayList<>();
        bots.add(new WebCrawler("https://www.nettavisen.no/", 1));


        /*
        bots.add(new WebCrawler("https://npr.org", 2));
        bots.add(new WebCrawler("https://nytimes.com", 3));

         */

        for(WebCrawler w : bots) {
            try {
                w.getThread().join();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
