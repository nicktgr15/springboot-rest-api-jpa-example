package com.nicktgr15.tid.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class ValidationResponse {

    @JsonView(Views.Public.class)
    public Boolean isValid;
    @JsonView(Views.Public.class)
    public List<String> enabledFeatures;

    public ValidationResponse(Boolean isValid, List<String> enabledFeatures) {
        this.isValid = isValid;
        this.enabledFeatures = enabledFeatures;
    }
}
