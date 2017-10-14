package com.nicktgr15.tid.db.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Version implements Serializable {

    @Id
    private String version;

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    private int enabled;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Version(String version, int enabled) {
        this.enabled = enabled;
        this.version = version;
    }

    public Version() {
    }

}
