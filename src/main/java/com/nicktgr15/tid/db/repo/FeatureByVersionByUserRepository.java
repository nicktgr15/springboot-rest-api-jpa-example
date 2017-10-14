package com.nicktgr15.tid.db.repo;

import com.nicktgr15.tid.db.model.FeatureByVersionByUser;
import com.nicktgr15.tid.db.model.User;
import com.nicktgr15.tid.db.model.Version;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureByVersionByUserRepository extends CrudRepository<FeatureByVersionByUser, User> {
    List<FeatureByVersionByUser> findByUserAndVersion(User user, Version version);
}
