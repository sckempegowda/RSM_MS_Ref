package com.commscope.rsm.configuration.provision.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.commscope.rsm.configuration.provision.exception.DeviceNotFoundException;
import com.commscope.rsm.configuration.provision.exception.ParameterNotFoundException;

@ControllerAdvice
@RestController
public class CustomResponseExceptionBuilder extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ResponseMetadata ResponseMetadata = new ResponseMetadata(new Date(), ex.getMessage());
		return new ResponseEntity<Object>(ResponseMetadata, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DeviceNotFoundException.class)
	public final ResponseEntity<Object> handleDeviceNotFoundException(DeviceNotFoundException ex, WebRequest request) throws Exception {
		String errorMessage = messageSource.getMessage(ex.getErrorCode().getErrorCodeKey(), ex.getArgs(), LocaleContextHolder.getLocale());
		ResponseMetadata ResponseMetadata = new ResponseMetadata(new Date(), errorMessage);
		return new ResponseEntity<Object>(ResponseMetadata, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ParameterNotFoundException.class)
	public final ResponseEntity<Object> handleParameterNotFoundException(ParameterNotFoundException ex, WebRequest request) throws Exception {
		ResponseMetadata ResponseMetadata = new ResponseMetadata(new Date(), ex.getMessage());
		return new ResponseEntity<Object>(ResponseMetadata, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ResponseMetadata ResponseMetadata = new ResponseMetadata(new Date(), ex.getMessage());
		return new ResponseEntity<Object>(ResponseMetadata, HttpStatus.BAD_REQUEST);
	}
}
