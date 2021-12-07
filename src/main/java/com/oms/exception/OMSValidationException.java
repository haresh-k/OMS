package com.oms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class OMSValidationException extends ResponseStatusException {
	public OMSValidationException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
