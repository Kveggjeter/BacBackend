package com.bac.bacbackend.data.scraper;

public class StringBank {

    protected static final String reutersUrl = "https://www.reuters.com/world/";
    protected final String reutersTxtLocator = "h3 a";
    protected final String reutersTxtHref = "href";
    protected final String reutersImgLocator = "div[data-testid='Image']";
    protected final String reutersImgHref = "innerHTML";
    protected final String reutersTitle = "h1";
    protected final String reutersSum = "div[data-testid^='paragraph-0']";

    protected static final String apUrl = "https://apnews.com/";
    protected final String apTxtLocator = "div.PagePromo-media a.Link[aria-label]";
    protected final String apTxtHref = "href";
    protected final String apImgLocator = apTxtLocator + " picture[data-crop]";
    protected final String apImgHref = "innerHTML";
    protected final String apTitle = "h1";
    protected final String apSum = ".RichTextStoryBody p";

    protected static final String ajUrl = "https://www.aljazeera.com/africa/";
    protected final String ajTxtLocator = "section#news-feed-container h3 a";
    protected final String ajTxtHref = "href";
    protected final String ajImgLocator = ".gc__image-wrap .responsive-image";
    protected final String ajImgHref = "innerHTML";
    protected final String ajTitle = "h1";
    protected final String ajSum = "p.article__subhead";

    protected static final String rtUrl = "https://www.rt.com/russia/";
    protected final String rtTxtLocator = "div.media__image a";
    protected final String rtTxtHref = "href";
    protected final String rtImgLocator = rtTxtLocator;
    protected final String rtImgHref = "innerHTML";
    protected final String rtTitle = "h1";
    protected final String rtSum = "div.article__summary";

    protected static final String anUrl = "https://asianews.network/category/home-page/daily-digest/";
    protected final String anTxtLocator = "div.post-img a";
    protected final String anTxtHref = "href";
    protected final String anImgLocator = anTxtLocator + " img";
    protected final String anImgHref = "src";
    protected final String anTitle = "h1.entry-title";
    protected final String anSum = "div.entry-content";



}
