package com.nicktgr15.tid.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotEmpty;

public class ValidationRequest {
    @JsonView(Views.Public.class)
    @NotEmpty
    public String version;

    @JsonView(Views.Public.class)
    public String username;

    public ValidationRequest(){

    }

    public ValidationRequest(String version, String username) {
        this.version = version;
        this.username = username;
    }
}
