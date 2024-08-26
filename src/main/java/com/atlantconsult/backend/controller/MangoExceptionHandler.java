package com.atlantconsult.backend.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.ConfigurationException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import com.atlantconsult.backend.BackendCfMangoApplication;

@ControllerAdvice
public class MangoExceptionHandler {
	
	static final Logger logger = LoggerFactory.getLogger(BackendCfMangoApplication.class);

	@ExceptionHandler(value = { IllegalArgumentException.class, ConfigurationException.class,
			RestClientException.class })
	public ResponseEntity<Object> defaultErrorHandler(Exception ex, HttpServletRequest request) throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		body.put("timestamp", LocalDateTime.now().toString());
		body.put("status", status.value());
		body.put("error", ex.getClass());
		body.put("message", ex.getMessage());
		body.put("path", request.getRequestURI());
		return new ResponseEntity<Object>(body, status);
	}

}
