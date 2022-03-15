package com.commscope.rsm.configuration.provision.utils;

public enum ErrorCode {
	DEVICE_NOT_FOUND("DEVICE_NOT_FOUND");
	
	private String errorCodeKey;
	
	private ErrorCode(String errorCodeKey) {
		this.errorCodeKey = errorCodeKey;
	}
	
	public String getErrorCodeKey() {
		return errorCodeKey;
	}
}