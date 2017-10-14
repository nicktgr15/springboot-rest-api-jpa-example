package com.nicktgr15.tid.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Feature {

    @Id
    private String feature;

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    private int enabled;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature1 = (Feature) o;

        if (enabled != feature1.enabled) return false;
        if (feature != null ? !feature.equals(feature1.feature) : feature1.feature != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = feature != null ? feature.hashCode() : 0;
        result = 31 * result + enabled;
        return result;
    }

    public Feature(String feature, int enabled) {
        this.feature = feature;

        this.enabled = enabled;
    }

    public Feature() {
    }

    @Override
    public String toString() {
        return "Feature{" +
                "feature='" + feature + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
