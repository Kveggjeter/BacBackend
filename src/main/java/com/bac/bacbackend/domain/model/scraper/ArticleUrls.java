package com.bac.bacbackend.domain.model.scraper;

/**
 * Record class for keeping tabs on what url to be scraped
 *
 * @param articleUrl url of the to-be-crawled webpage
 * @param imgUrl url of the image displaying the article
 */
public record ArticleUrls(String articleUrl, String imgUrl) {}
