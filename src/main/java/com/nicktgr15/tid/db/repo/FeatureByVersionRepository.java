package com.nicktgr15.tid.db.repo;

import com.nicktgr15.tid.db.model.FeatureByVersion;
import com.nicktgr15.tid.db.model.Version;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureByVersionRepository extends CrudRepository<FeatureByVersion, Version> {
    List<FeatureByVersion> findByVersion(Version version);
}
