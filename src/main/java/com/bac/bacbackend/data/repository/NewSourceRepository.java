package com.bac.bacbackend.data.repository;

import com.bac.bacbackend.domain.model.NewSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewSourceRepository extends CrudRepository<NewSource, String> {
}
