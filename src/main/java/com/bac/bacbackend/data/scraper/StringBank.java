package com.bac.bacbackend.data.scraper;

import java.util.ArrayList;

public class StringBank {

    public ArrayList<String> getUrl() {
        ArrayList<String> url = new ArrayList<String>();
        url.add(reutersUrl);
        url.add(reUrl);
        url.add(apUrl);
        url.add(ajUrl);
        url.add(rtUrl);
        url.add(anUrl);
        url.add(skyUrl);
        url.add(americasUrl);
        return url;
    }

    protected ArrayList<String> getTxtLocator() {
        ArrayList<String> txtLocator = new ArrayList<String>();
        txtLocator.add(reutersTxtLocator);
        txtLocator.add(reutersTxtLocator);
        txtLocator.add(apTxtLocator);
        txtLocator.add(ajTxtLocator);
        txtLocator.add(rtTxtLocator);
        txtLocator.add(anTxtLocator);
        txtLocator.add(skyTxtLocator);
        txtLocator.add(reutersTxtLocator);
        return txtLocator;
    }

    protected ArrayList<String> getTxtHref() {
        ArrayList<String> txtHref = new ArrayList<String>();
        txtHref.add(reutersTxtHref);
        txtHref.add(reutersTxtHref);
        txtHref.add(apTxtHref);
        txtHref.add(ajTxtHref);
        txtHref.add(rtTxtHref);
        txtHref.add(anTxtHref);
        txtHref.add(skyTxtHref);
        txtHref.add(reutersTxtHref);
        return txtHref;
    }

    protected ArrayList<String> getImgLocator() {
        ArrayList<String> imgLocator = new ArrayList<String>();
        imgLocator.add(reutersImgLocator);
        imgLocator.add(reutersImgLocator);
        imgLocator.add(apImgLocator);
        imgLocator.add(ajImgLocator);
        imgLocator.add(rtImgLocator);
        imgLocator.add(anImgLocator);
        imgLocator.add(skyImgLocator);
        imgLocator.add(reutersImgLocator);
        return imgLocator;
    }

    protected ArrayList<String> getImgHref() {
        ArrayList<String> imgHref = new ArrayList<String>();
        imgHref.add(reutersImgHref);
        imgHref.add(reutersImgHref);
        imgHref.add(apImgHref);
        imgHref.add(ajImgHref);
        imgHref.add(rtImgHref);
        imgHref.add(anImgHref);
        imgHref.add(skyImgHref);
        imgHref.add(reutersImgHref);
        return imgHref;
    }

    protected ArrayList<String> getTitle() {
        ArrayList<String> tit = new ArrayList<String>();
        tit.add(reutersTitle);
        tit.add(reutersTitle);
        tit.add(apTitle);
        tit.add(ajTitle);
        tit.add(rtTitle);
        tit.add(anTitle);
        tit.add(skyTitle);
        tit.add(reutersTitle);
        return tit;
    }

    protected ArrayList<String> getSummary() {
        ArrayList<String> sum = new ArrayList<String>();
        sum.add(reutersSum);
        sum.add(reutersSum);
        sum.add(apSum);
        sum.add(ajSum);
        sum.add(rtSum);
        sum.add(anSum);
        sum.add(skySum);
        sum.add(reutersSum);
        return sum;
    }

    /**
     * REUTERS WORLD
     */
    protected static final String reutersUrl = "https://www.reuters.com/world/";
    protected final String reutersTxtLocator = "h3 a";
    protected final String reutersTxtHref = "href";
    protected final String reutersImgLocator = "div[data-testid='Image']";
    protected final String reutersImgHref = "innerHTML";
    protected final String reutersTitle = "h1";
    protected final String reutersSum = "div[data-testid^='paragraph-0']";

    /**
     * REUTERS EUROPE
     */
    protected static final String reUrl = "https://www.reuters.com/world/europe/";

    /**
     * AP
     */
    protected static final String apUrl = "https://apnews.com/";
    protected final String apTxtLocator = "div.PagePromo-media a.Link[aria-label]";
    protected final String apTxtHref = "href";
    protected final String apImgLocator = apTxtLocator + " picture[data-crop]";
    protected final String apImgHref = "innerHTML";
    protected final String apTitle = "h1";
    protected final String apSum = ".RichTextStoryBody p";

    /**
     * ALJEZERAH
     */
    protected static final String ajUrl = "https://www.aljazeera.com/africa/";
    protected final String ajTxtLocator = "section#news-feed-container h3 a";
    protected final String ajTxtHref = "href";
    protected final String ajImgLocator = ".gc__image-wrap .responsive-image";
    protected final String ajImgHref = "innerHTML";
    protected final String ajTitle = "h1";
    protected final String ajSum = "p.article__subhead";

    /**
     * RT
     */
    protected static final String rtUrl = "https://www.rt.com/russia/";
    protected final String rtTxtLocator = "div.media__image a";
    protected final String rtTxtHref = "href";
    protected final String rtImgLocator = rtTxtLocator;
    protected final String rtImgHref = "innerHTML";
    protected final String rtTitle = "h1";
    protected final String rtSum = "div.article__summary";

    /**
     * ASIA NEWS
     */
    protected static final String anUrl = "https://asianews.network/category/home-page/daily-digest/";
    protected final String anTxtLocator = "div.post-img a";
    protected final String anTxtHref = "href";
    protected final String anImgLocator = anTxtLocator + " img";
    protected final String anImgHref = "src";
    protected final String anTitle = "h1.entry-title";
    protected final String anSum = "div.entry-content";

    /**
     * National Indigenous Times
     *
     * VERY STRICT CLOUDFLARE, MAYBE NOT WORTH?
     *
     */
    protected static final String nisUrl = "https://nit.com.au/posts/pacific";
    protected final String nisTxtLocator = "a.post-card-component-heading-link";
    protected final String nisTxtHref = "href";
    protected final String nisImgLocator = "figure.post-card-component";
    protected final String nisImgHref = "outerHTML";
    protected final String nisTitle = "h1";
    protected final String nisSum = "div.article-content";

    /**
     * SKY.AUS
     */
    protected static final String skyUrl = "https://www.skynews.com.au/australia-news";
    protected final String skyTxtLocator = "h4 a";
    protected final String skyTxtHref = "href";
    protected final String skyImgLocator = "div.responsive-img";
    protected final String skyImgHref = "innerHTML";
    protected final String skyTitle = "h1";
    protected final String skySum = "div.description p";

    /**
     * Reuters americas
     */
    private static final String americasUrl = "https://www.reuters.com/world/americas/";



}
