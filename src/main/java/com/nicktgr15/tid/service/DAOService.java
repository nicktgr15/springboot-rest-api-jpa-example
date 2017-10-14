package com.nicktgr15.tid.service;

import com.nicktgr15.tid.db.model.Feature;
import com.nicktgr15.tid.db.model.FeatureByVersionByUser;
import com.nicktgr15.tid.db.model.User;
import com.nicktgr15.tid.db.model.Version;
import com.nicktgr15.tid.db.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DAOService {

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VersionRepository versionRepository;

    @Autowired
    FeatureByVersionRepository featureByVersionRepository;

    @Autowired
    FeatureByVersionByUserRepository featureByVersionByUserRepository;

    public List<Feature> getFeaturesByUserAndVersion(Version version, User user){
        List<FeatureByVersionByUser> byUserAndVersion =
                featureByVersionByUserRepository.findByUserAndVersion(
                        user, version);

        return byUserAndVersion.stream().map(
                f -> {
                    Feature featureWithNewState = f.getFeature();
                    featureWithNewState.setEnabled(f.getEnabled());
                    return featureWithNewState;
                }
        ).collect(Collectors.toList());
    }

    public Version getVersion(String version){
        return versionRepository.findByVersion(version);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<Feature> getEnabledFeatures(){
        return featureRepository.findByEnabled(1);
    }

    public List<Feature> getFeaturesByVersion(Version v){
        return featureByVersionRepository
                .findByVersion(v).stream()
                .map(f -> {
                    Feature featureWithNewState = f.getFeature();
                    featureWithNewState.setEnabled(f.getEnabled());
                    return featureWithNewState;
                })
                .collect(Collectors.toList());
    }
}
