package com.bac.bacbackend.data.scraper.config;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;
import java.util.List;
/**
 * Ground class for collecting sources for scraping. Might make this even more
 * generic for the sake of OOP later on.
 */
public abstract class DataSourceHandler<T, ID> {

    private final CrudRepository<T, ID> repo;

    protected DataSourceHandler(CrudRepository<T, ID> repo) {
        this.repo = repo;
    }

    /**
     * Return all rows as a List.
     * @return
     */
    protected List<T> findAll() {
        List<T> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    }

    /**
     * Size of object, used for determing iterations
     * @return
     */
    protected int size() {
        return (int) repo.count() - 1;
    }

    /**
     * Fetching one row
     * @param index
     * @return
     */
    protected T get(int index) {
        List<T> all = findAll();
        return all.get(index);
    }

    /**
     * Adding a value
     */
    protected void add(T t) {
        repo.save(t);
    }
}
