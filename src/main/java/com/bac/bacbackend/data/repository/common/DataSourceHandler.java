package com.bac.bacbackend.data.repository.common;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic base class for each repository. It uses the CrudRepository from Spring for the
 * boilerplate, but opens up some further optimized possibilities towards the db
 *
 * @param <T> generic tag representing what entity to use
 * @param <ID> generic tag representing what type of data to be used
 */
public abstract class DataSourceHandler<T, ID> {

    private final CrudRepository<T, ID> repo;

    protected DataSourceHandler(CrudRepository<T, ID> repo) {
        this.repo = repo;
    }

    /**
     * Return all rows as a List.
     * @return a generic list
     */
    protected List<T> findAll() {
        List<T> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    }

    /**
     * Size of object, used for determine iterations. - 1 for preventing indexOutOfBounds
     * @return amount of news sources
     */
    protected int size() {
        return (int) repo.count() - 1;
    }

    /**
     * Overload method. Fetching one row based on the index given
     * @param index index for what data class to get
     * @return all that matches with the index
     */
    protected T get(int index) {
        List<T> all = findAll();
        return all.get(index);
    }

    /**
     * copy of existing method in spring data, but with the possibility for children classes to
     * override or differ the name.
     * @param id id of the
     * @return a boolean check for if the chosen data exist.
     */
    protected boolean existById(ID id) {
        return repo.existsById(id);
    }

    /**
     * Adding a value to the db
     * @param t saving the parameterized data
     */
    protected void add(T t) {
        repo.save(t);
    }
}
