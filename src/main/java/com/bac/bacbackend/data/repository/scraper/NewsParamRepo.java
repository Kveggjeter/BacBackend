package com.bac.bacbackend.data.repository.scraper;

import com.bac.bacbackend.data.model.scraper.NewsParamEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.common.DataMapper;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import org.springframework.stereotype.Component;

/**
 * Brigde between websource and the scraper functionality. Might even drive this
 * yet another level up the architecture, but I'll need to abstract the scraping some more
 * TODO: re(move)?
 */
@Component
public class NewsParamRepo extends DataSourceHandler<NewsParamEntity, String> implements INewsParamRepo {

    public NewsParamRepo(NewsParamDataRepo repo) {
        super(repo);
    }

    @Override
    public ScrapeProps select(int n) {
        NewsParamEntity en = get(n);
        return DataMapper.toDomain(en, ScrapeProps.class);
    }

    @Override
    public int getCount() {
        return size();
   }

}
