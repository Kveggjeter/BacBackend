package com.bac.bacbackend.data.scraper.config;

import com.bac.bacbackend.data.repository.NewSourceRepository;
import com.bac.bacbackend.domain.model.NewSource;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Brigde between websource and the scraper functionality. Might even drive this
 * yet another level up the architecture, but I'll need to abstract the scraping some more
 * TODO: re(move)?
 */
@Component
public class WebSetter extends WebSource<NewSource, String> {

    public WebSetter(NewSourceRepository nsRepo) {
        super(nsRepo);
    }

    public List<String> getUrl() {
        return getData(NewSource::getUrl);
    }

    public List<String> getTxtLocator() {
        return getData(NewSource::getTxtLocator);
    }

    public List<String> getTxtHref() {
        return getData(NewSource::getTxtHref);
    }

    public List<String> getImgLocator() {
        return getData(NewSource::getImgLocator);
    }

    public List<String> getImgHref() {
        return getData(NewSource::getImgHref);
    }

    public List<String> getTitle() {
        return getData(NewSource::getTitle);
    }

    public List<String> getSum() {
        return getData(NewSource::getSum);
    }

    public int getCount() { return countSource(); }

    public ArrayList<String> make(int n) {
        ArrayList<String> sources = new ArrayList<>();
        sources.add(getUrl().get(n));
        sources.add(getTxtLocator().get(n));
        sources.add(getTxtHref().get(n));
        sources.add(getImgLocator().get(n));
        sources.add(getImgHref().get(n));
        sources.add(getTitle().get(n));
        sources.add(getSum().get(n));
        return sources;
    }
}
