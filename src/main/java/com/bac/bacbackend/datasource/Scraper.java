package com.bac.bacbackend.datasource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraper {

    public void scrape() {
        String url = "https://www.nettavisen.no/nyheter/uklart-nar-trumps-tollplaner-settes-ut-i-livet/s/5-95-2266563";

        try {
            Document document = Jsoup.connect(url).get();
            Elements article = document.select(".nettavisen-theme");

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
