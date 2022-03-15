package com.commscope.rsm.configuration.provision.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.commscope.rsm.configuration.provision.utils.ErrorCode;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2441678398149680138L;

	private ErrorCode errorCode;
	
	private String[] args;

	public DeviceNotFoundException() {
		super();
	}

	public DeviceNotFoundException(ErrorCode errorCode, String... args) {
		super();
		this.errorCode = errorCode;
		this.args = args;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String[] getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		return "Device does not exists";
	}

}
