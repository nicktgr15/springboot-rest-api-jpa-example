package com.nicktgr15.tid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidVersionException extends RuntimeException{

    public InvalidVersionException() {
        super();
    }
}
