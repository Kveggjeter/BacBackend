package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.FailedEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.port.IFailedRepo;
import org.springframework.stereotype.Component;

/**
 * Repository for articles that is deemed not worthy as articles, so the application
 * don't waste throwing a lot of exceptions for fun when trying to handle them.
 */
@Component
public class FailedRepo extends DataSourceHandler<FailedEntity, String> implements IFailedRepo {

    public FailedRepo( FailedDataRepo repo) { super(repo); }

    /**
     * Checks if the object exists
     *
     * @param id id of the object in question
     * @return if it exists
     */
    @Override
    public boolean exists(String id) { return existById(id); }

    /**
     * Adding a failed url to the db
     *
     * @param failedUrl url that failed being scraped
     */
    public void addToFail(String failedUrl) {
        FailedEntity entity = new FailedEntity();
        entity.setUrl(failedUrl);
        add(entity);
    }
}
