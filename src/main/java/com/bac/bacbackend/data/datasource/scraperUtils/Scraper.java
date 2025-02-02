package com.bac.bacbackend.data.datasource.scraperUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraper {

    public void scrape() {
        String url = "https://www.reuters.com/business/trump-stretches-trade-law-boundaries-with-canada-mexico-china-tariffs-2025-02-02/";

        try {
            Document document = Jsoup.connect(url).get();
            Elements article = document.select("main");

            for (Element ac: article) {
                String title = ac.select("h1").text();

                System.out.println(title + " ");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
