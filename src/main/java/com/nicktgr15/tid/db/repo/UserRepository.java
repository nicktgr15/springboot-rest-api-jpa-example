package com.nicktgr15.tid.db.repo;

import com.nicktgr15.tid.db.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

}
