package com.bac.bacbackend.data.scraper.config;

import com.bac.bacbackend.data.repository.NewSourceRepository;
import com.bac.bacbackend.data.model.NewSource;
import com.bac.bacbackend.domain.model.SourceDto;
import org.springframework.stereotype.Component;

/**
 * Brigde between websource and the scraper functionality. Might even drive this
 * yet another level up the architecture, but I'll need to abstract the scraping some more
 * TODO: re(move)?
 */
@Component
public class WebSetter extends WebSource<NewSource, String> {

    public WebSetter(NewSourceRepository repo) {
        super(repo);
    }

   public SourceDto selectors(int n) {
        NewSource src = get(n);
        return new SourceDto(
                src.getUrl(),
                src.getTxtLocator(),
                src.getTxtHref(),
                src.getImgLocator(),
                src.getImgHref(),
                src.getTitle(),
                src.getSum()
        );
   }

   public int getCount() {
        return size();
   }
}
