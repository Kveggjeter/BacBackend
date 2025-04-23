package com.bac.bacbackend.domain.common.validators;

import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.IFailedRepo;
import org.springframework.stereotype.Component;

@Component
public class ArticleUrlValidator {
    private final IArticleRepo articleRepo;
    private final IFailedRepo failedRepo;

    private ArticleUrlValidator(IArticleRepo articleRepo, IFailedRepo failedRepo) {
        this.articleRepo = articleRepo;
        this.failedRepo = failedRepo;
    }

    public boolean isArticleAlreadyAdded(String url) {
        return url != null
                && !articleRepo.exists(url)
                && !failedRepo.exists(url);
    }
}