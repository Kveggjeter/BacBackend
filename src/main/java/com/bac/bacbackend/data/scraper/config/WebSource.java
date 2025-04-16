package com.bac.bacbackend.data.scraper.config;

import com.bac.bacbackend.data.repository.NewSourceRepository;
import com.bac.bacbackend.domain.model.NewSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Ground class for collecting sources for scraping. Might make this even more
 * generic for the sake of OOP later on.
 */
public class WebSource {

    protected final Collection<NewSource> ns;

    protected WebSource(NewSourceRepository nsRepo) {
        this.ns = (Collection<NewSource>) nsRepo.findAll();
    }

    protected <T> List<T> getData(Function<NewSource, T> f) {
        return ns.stream()
                .filter(Objects::nonNull)
                .map(f)
                .collect(Collectors.toList());
    }
}
