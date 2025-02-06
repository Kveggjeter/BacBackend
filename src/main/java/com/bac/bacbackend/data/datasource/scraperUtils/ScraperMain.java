//package com.bac.bacbackend.data.datasource.scraperUtils;
//
//import java.util.List;
//
//public class ScraperMain {
//    private static final String url = "https://www.reuters.com/world/";
//    private static final int max = 3;
//
//    public static void main(String[] args) {
//        WebCrawler crawler = new WebCrawler();
//        List<String> images = crawler.getImgUrls();
//        List<String> articles = crawler.startCrawling(max, url);
//
//        for (int i = 0; i < articles.size(); i++) {
//            System.out.println("Article: " + articles.get(i));
//            System.out.println("Image: " + images.get(i));
//        }
//
//    }
//}
