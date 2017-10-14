package com.nicktgr15.tid.db.repo;

import com.nicktgr15.tid.db.model.Version;
import org.springframework.data.repository.CrudRepository;

public interface VersionRepository extends CrudRepository<Version, String> {

    Version findByVersion(String version);

}
