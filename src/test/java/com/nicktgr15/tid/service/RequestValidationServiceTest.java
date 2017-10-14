package com.nicktgr15.tid.service;

import com.google.common.collect.Lists;
import com.nicktgr15.tid.db.model.Feature;
import com.nicktgr15.tid.db.model.User;
import com.nicktgr15.tid.db.model.Version;
import com.nicktgr15.tid.exception.InvalidVersionException;
import com.nicktgr15.tid.model.ValidationRequest;
import com.nicktgr15.tid.model.ValidationResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestValidationServiceTest {

    @Mock
    DAOService daoService;

    @InjectMocks
    RequestValidationService requestValidationService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test(expected = InvalidVersionException.class)
    public void test_ShouldThrowInvalidVersionException_WhenNoVersionIsProvided() throws Exception {
        requestValidationService.validate(new ValidationRequest(null, null));
    }

    @Test(expected = InvalidVersionException.class)
    public void test_ShouldThrowInvalidVersionException_WhenVersionProvidedIsInvalid() throws Exception {
        when(daoService.getVersion("1.1.0")).thenReturn(null);
        requestValidationService.validate(new ValidationRequest("1.1.0", null));
    }

    @Test
    public void test_ShouldReturnNotEnabled_WhenVersionIsDisabledGlobally() throws Exception {
        when(daoService.getVersion("1.1.0")).thenReturn(new Version("1.1.0", 0));
        ValidationResponse validationResponse = requestValidationService.validate(
                new ValidationRequest("1.1.0", null));
        assertEquals(false, validationResponse.isValid);
    }

    @Test
    public void test_ShouldReturnGlobbalyEnabledFeatures_WhenNoVersionSpecificFeatureConfigExists() {
        Version v = new Version("1.1.0", 1);
        when(daoService.getVersion("1.1.0")).thenReturn(v);
        when(daoService.getFeaturesByVersion(v)).thenReturn(Lists.newArrayList());
        Feature f1 = new Feature("featureA", 1);
        Feature f2 = new Feature("featureB", 1);
        when(daoService.getEnabledFeatures()).thenReturn(Lists.newArrayList(
                f1, f2
        ));
        ValidationResponse validationResponse = requestValidationService.validate(
                new ValidationRequest("1.1.0", null));

        assertThat(validationResponse.enabledFeatures, CoreMatchers.hasItems(
                "featureA", "featureB"));
    }

    @Test
    public void test_ShouldFilterVersionSpecificDisabledFeatures() {
        Version v = new Version("1.1.0", 1);
        when(daoService.getVersion("1.1.0")).thenReturn(v);

        Feature f1 = new Feature("featureA", 1);
        Feature f2 = new Feature("featureB", 1);

        when(daoService.getFeaturesByVersion(v)).thenReturn(Lists.newArrayList(
                new Feature("featureA", 0)
        ));
        when(daoService.getEnabledFeatures()).thenReturn(Lists.newArrayList(
                f1, f2
        ));
        ValidationResponse validationResponse = requestValidationService.validate(
                new ValidationRequest("1.1.0", null));

        assertThat(validationResponse.enabledFeatures, CoreMatchers.hasItems(
                "featureB"
        ));
    }

    @Test
    public void test_ShouldFilterUserSpecificDisabledFeatures() {
        Version v = new Version("1.1.0", 1);
        when(daoService.getVersion("1.1.0")).thenReturn(v);

        Feature f1 = new Feature("featureA", 1);
        Feature f2 = new Feature("featureB", 1);
        User user = new User("myUsername");

        when(daoService.getFeaturesByVersion(v)).thenReturn(Lists.newArrayList(
        ));
        when(daoService.getEnabledFeatures()).thenReturn(Lists.newArrayList(
                f1, f2
        ));
        when(daoService.getFeaturesByUserAndVersion(v, user)).thenReturn(Lists.newArrayList(
                new Feature("featureA", 0)
        ));
        when(daoService.getUser("myUsername")).thenReturn(user);

        ValidationResponse validationResponse = requestValidationService.validate(
                new ValidationRequest("1.1.0", "myUsername"));
        assertThat(validationResponse.enabledFeatures, CoreMatchers.hasItems(
                "featureB"
        ));
    }

    @Test
    public void test_ShouldBeAbleToEnableUserSpecificFeatures() {
        Version v = new Version("1.1.0", 1);
        when(daoService.getVersion("1.1.0")).thenReturn(v);

        Feature f1 = new Feature("featureA", 1);
        Feature f2 = new Feature("featureB", 1);
        Feature f3 = new Feature("featureC", 0);
        User user = new User("myUsername");

        when(daoService.getFeaturesByVersion(v)).thenReturn(Lists.newArrayList(
        ));
        when(daoService.getEnabledFeatures()).thenReturn(Lists.newArrayList(
                f1, f2
        ));
        when(daoService.getFeaturesByUserAndVersion(v, user)).thenReturn(Lists.newArrayList(
                new Feature("featureC", 1)
        ));
        when(daoService.getUser("myUsername")).thenReturn(user);

        ValidationResponse validationResponse = requestValidationService.validate(
                new ValidationRequest("1.1.0", "myUsername"));

        assertThat(validationResponse.enabledFeatures,
                CoreMatchers.hasItems("featureA", "featureB", "featureC"));
    }

}