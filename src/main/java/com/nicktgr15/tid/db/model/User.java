package com.nicktgr15.tid.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username) {

        this.username = username;
    }

    public User() {

    }

    @Id
    private String username;
}
