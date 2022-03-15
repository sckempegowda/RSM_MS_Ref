package com.commscope.rsm.configuration.provision.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ParameterNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8903024168076839584L;

	@Override
	public String getMessage() {
        return "Parameter is not configured on the device";
    }
	
}
