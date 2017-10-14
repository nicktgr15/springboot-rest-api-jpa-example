package com.nicktgr15.tid.controller;

import com.google.common.collect.ImmutableList;
import com.nicktgr15.tid.model.ValidationRequest;
import com.nicktgr15.tid.model.ValidationResponse;
import com.nicktgr15.tid.service.RequestValidationService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValidationControllerTest {

    @Mock
    RequestValidationService requestValidationService;

    @InjectMocks
    ValidationController validationController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(validationController)
                .build();
    }

    @Test
    public void test_Return400WhenNoVersionIsProvided() throws Exception {
        mockMvc.perform(post("/validate")
                .contentType(APPLICATION_JSON).content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void test_ReturnFalseAndNoFeaturesWhenVersionIsDisabled() throws Exception {
        when(requestValidationService.validate(any(ValidationRequest.class))).thenReturn(
                new ValidationResponse(false, ImmutableList.of())
        );
        mockMvc.perform(post("/validate")
                .contentType(APPLICATION_JSON).content("{\"version\": \"1.1.0\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", Matchers.is(false)))
                .andReturn();
    }

    @Test
    public void test_ReturnTrueAndListOfEnabledFeatures_WhenVersionIsEnabled() throws Exception {
        when(requestValidationService.validate(any(ValidationRequest.class))).thenReturn(
                new ValidationResponse(true, ImmutableList.of(
                        "featureA", "featureB"
                ))
        );
        mockMvc.perform(post("/validate")
                .contentType(APPLICATION_JSON).content("{\"version\": \"1.1.0\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", Matchers.is(true)))
                .andExpect(jsonPath("$.enabledFeatures", Matchers.hasSize(2)))
                .andReturn();
    }
}