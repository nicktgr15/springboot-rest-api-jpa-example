package com.nicktgr15.tid.db.repo;

import com.nicktgr15.tid.db.model.Feature;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FeatureRepository extends CrudRepository<Feature, String> {

    List<Feature> findByEnabled(int enabled);

}

