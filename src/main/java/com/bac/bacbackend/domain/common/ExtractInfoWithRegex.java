package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.common.exceptions.RegexMatchResultException;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IWebSelectors;
import org.springframework.stereotype.Component;

@Component
public class ExtractInfoWithRegex extends Regex {

    private final IWebSelectors webSelectors;

    public ExtractInfoWithRegex(IWebSelectors webSelectors) {
        this.webSelectors = webSelectors;
    }

    public String getNameOfUrlSource(ScrapeContext scrapeContext) throws RegexMatchResultException {
        return urlName(scrapeContext.getArticleUrl());
    }

    public String getCorrectImg(ScrapeContext scrapeContext) {
        if (scrapeContext.getImgUrl() != null)
            return imgSrc(scrapeContext.getImgUrl());
        else return webSelectors.redo("img") != null
                ? imgSrc(webSelectors.redo("img")) : "NO_IMAGE";
    }

}
