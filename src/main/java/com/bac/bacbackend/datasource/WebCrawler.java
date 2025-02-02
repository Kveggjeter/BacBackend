package com.bac.bacbackend.datasource;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WebCrawler implements Runnable {

    private Thread thread;
    private String first_link;
    private int ID;

    public WebCrawler(String link, int num) {
        System.out.println("WebCrawler created");
        first_link = link;
        ID = num;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        crawl(first_link);
    }

    private void crawl(String url) {
        Document doc = request(url);

        if (doc != null) {

            Element firstLink = doc.selectFirst("article a");

            if (firstLink != null) {
                String nextLink = firstLink.absUrl("href");

                if (!nextLink.isEmpty()) {
                    System.out.println("Fant siste artikkel: " + nextLink);
                } else {
                    System.out.println("Ingen gyldig lenke funnet.");
                }
            } else {
                System.out.println("Ingen lenke funnet i .contents-wrapper.");
            }
        }
    }


    private Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                System.out.println("\n**Bot ID:" + ID + " Received webpage at " + url);
                System.out.println("Tittel: " + doc.title());

                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Thread getThread() {
        return thread;
    }
}
