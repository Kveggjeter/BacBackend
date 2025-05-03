package com.bac.bacbackend.domain.model.scraper;

/**
 *  Data record class used for giving instructions to both the scraper and the crawler of what information we are
 *  looking for. Is determined by indexing the list fetched from Redis that contain each of the attributes for each
 *  new-source. The record class are instantiated and used as object.
 *
 * @param url the url of landing page to the new source that are to be scraped
 * @param txtLocator locator for where to find the text containing the a element (the url)
 * @param txtHref deciding how to get that information and get the url to be saved further
 * @param imgLocator locator for the thumbnail-image
 * @param imgHref deciding how to extract the image, usually by clean innerHTML but sometimes "src" are enough
 * @param title locator for the title
 * @param sum locator for the summary of the article
 */
public record ScrapingProperties(
        String url,
        String txtLocator,
        String txtHref,
        String imgLocator,
        String imgHref,
        String title,
        String sum
) {}
