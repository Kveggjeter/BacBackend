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



}
