package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.common.exceptions.RegexMatchResultException;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IWebSelectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Child class of Regex. Primarily functions as a controller and an exception thrower
 */
@Component
public class ExtractInfoWithRegex extends Regex {

    private final IWebSelectors webSelectors;

    public ExtractInfoWithRegex(IWebSelectors webSelectors) {
        this.webSelectors = webSelectors;
    }

    /**
     * Controller for the base method, but helps to throw an unexpected exception upwards in the hierarchy
     *
     * @param scrapeContext data class containing the context of the scraped article
     * @return the name of the url found in scrapeContext
     * @throws RegexMatchResultException throws an exception to be caught higher up
     */
    public String getNameOfUrlSource(ScrapeContext scrapeContext) throws RegexMatchResultException {
        return urlName(scrapeContext.getArticleUrl());
    }

    /**
     * Controller for base method, does not throw an exception but handles the "Null" value. Images can be
     * difficult to get, as some webpages does not have thumbnail images on all their articles and sometimes
     * the server is just slow to load media and therefor the src files. This is allowed to be null and
     * are treated with a NO_IMAGE if we don't succeed after out last hail-mary attempt.
     *
     * @param scrapeContext data class containing the context of the scraped article
     * @return hopefully the url that holds the biggest iteration of the image
     */
    public String getCorrectImg(ScrapeContext scrapeContext) {
        if (scrapeContext.getImgUrl() != null)
            return imgSrc(scrapeContext.getImgUrl());
        else return webSelectors.redo("img") != null
                ? imgSrc(webSelectors.redo("img")) : "NO_IMAGE";
    }

}
