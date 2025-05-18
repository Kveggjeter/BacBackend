package com.bac.bacbackend.domain.common.validators;

import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.IFailedRepo;
import org.springframework.stereotype.Component;

/**
 * Validator for finding out if a article exist or not in Redis
 */
@Component
public class ArticleUrlValidator {
    private final IArticleRepo articleRepo;
    private final IFailedRepo failedRepo;

    public ArticleUrlValidator(IArticleRepo articleRepo, IFailedRepo failedRepo) {
        this.articleRepo = articleRepo;
        this.failedRepo = failedRepo;
    }

    /**
     * Checks for the existence of the url in both the failed repo and the article repo.
     *
     * @param url given url we want to add
     * @return boolean operator if the url exist from before
     */
    public boolean isArticleAlreadyAdded(String url) {
        return url != null
                && !articleRepo.exists(url)
                && !failedRepo.exists(url);
    }
}