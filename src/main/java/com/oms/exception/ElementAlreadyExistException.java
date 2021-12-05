package com.oms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class ElementAlreadyExistException extends ResponseStatusException {
	public ElementAlreadyExistException(String message){
        super(HttpStatus.BAD_REQUEST, message);
 }
}
