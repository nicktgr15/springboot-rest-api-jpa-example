package com.nicktgr15.tid.controller;

import com.nicktgr15.tid.model.ValidationRequest;
import com.nicktgr15.tid.model.ValidationResponse;
import com.nicktgr15.tid.service.RequestValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ValidationController {

    @Autowired
    RequestValidationService requestValidationService;

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse validate(@Valid @RequestBody ValidationRequest vr)
            throws IOException {
        return requestValidationService.validate(vr);
    }

}
