package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.FailedEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.port.IFailedRepo;
import org.springframework.stereotype.Component;

@Component
public class FailedRepo extends DataSourceHandler<FailedEntity, String> implements IFailedRepo {

    public FailedRepo( FailedDataRepo repo) { super(repo); }

    @Override
    public boolean exists(String id) { return existById(id); }

    public void adder(String s) {
        FailedEntity e = new FailedEntity();
        e.setUrl(s);
        add(e);
    }
}
