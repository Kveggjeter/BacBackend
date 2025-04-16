package com.bac.bacbackend.data.scraper.config;

import com.bac.bacbackend.data.repository.NewSourceRepository;
import com.bac.bacbackend.domain.model.NewSource;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Ground class for collecting sources for scraping. Might make this even more
 * generic for the sake of OOP later on.
 */
public abstract class WebSource<T, ID> {

    protected final Collection<T> item;

    protected WebSource(CrudRepository<T, ID> repo) {
        this.item = (Collection<T>) repo.findAll();
    }

    protected <R> List<R> getData(Function<T, R> f) {
        return item.stream()
                .filter(Objects::nonNull)
                .map(f)
                .collect(Collectors.toList());
    }

    protected int countSource() { return item.size() - 1; }
}
