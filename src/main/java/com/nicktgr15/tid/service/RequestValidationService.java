package com.nicktgr15.tid.service;

import com.google.common.collect.ImmutableList;
import com.nicktgr15.tid.db.model.Feature;
import com.nicktgr15.tid.db.model.FeatureByVersionByUser;
import com.nicktgr15.tid.db.model.User;
import com.nicktgr15.tid.db.model.Version;
import com.nicktgr15.tid.db.repo.*;
import com.nicktgr15.tid.exception.InvalidVersionException;
import com.nicktgr15.tid.model.ValidationRequest;
import com.nicktgr15.tid.model.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidationService.class);

    @Autowired
    DAOService daoService;

    public ValidationResponse validate(ValidationRequest vr) {
        if (vr.version == null) {
            throw new InvalidVersionException();
        }

        Version v = daoService.getVersion(vr.version);

        if (v == null) {
            LOGGER.error("Version '" + vr.version + "' is invalid");
            throw new InvalidVersionException();
        }

        if (v.getEnabled() == 0) {
            LOGGER.info("Version '" +v.getVersion()+ "' is disabled globally");
            return new ValidationResponse(false, ImmutableList.of());
        }

        List<Feature> globallyEnabledFeatures = daoService.getEnabledFeatures();
        List<Feature> featuresByVersion = daoService.getFeaturesByVersion(v);

        List<Feature> newFeatures = applyDiffToFeatures(
                globallyEnabledFeatures, featuresByVersion);

        if(vr.username == null){
            LOGGER.info("Not filtering by user as username not provided in request");
            return new ValidationResponse(true, featureNamesOnly(
                    enabledOnly(newFeatures)));
        }

        User user = daoService.getUser(vr.username);

        if(user == null){
            LOGGER.info("User with username '" + vr.username + "' not found");
            return new ValidationResponse(true, featureNamesOnly(
                    enabledOnly(featuresByVersion)));
        }

        List<Feature> featuresByUserAndVersion =
                daoService.getFeaturesByUserAndVersion(v, user);

        newFeatures = applyDiffToFeatures(
                newFeatures, featuresByUserAndVersion);

        return new ValidationResponse(true, featureNamesOnly(
                enabledOnly(newFeatures)));

    }

    private List<Feature> enabledOnly(List<Feature> features){
        return features.stream()
                .filter(f -> f.getEnabled() == 1)
                .collect(Collectors.toList());
    }

    private List<String> featureNamesOnly(List<Feature> features){
        return features.stream().map(Feature::getFeature).collect(Collectors.toList());
    }


    private List<Feature> applyDiffToFeatures(List<Feature> features, List<Feature> diff){
        Map<String, Integer> newFeatures = new HashMap<>();

        for (Feature feature : features) {
            newFeatures.put(feature.getFeature(), feature.getEnabled());
        }
        for (Feature feature : diff) {
            newFeatures.put(feature.getFeature(), feature.getEnabled());
        }

        return newFeatures.entrySet()
                .stream().map(
                        e -> new Feature(e.getKey(), e.getValue())
                ).collect(Collectors.toList());
    }

}
