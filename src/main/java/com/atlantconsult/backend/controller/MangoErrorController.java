package com.atlantconsult.backend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlantconsult.backend.BackendCfMangoApplication;

@RestController
public class MangoErrorController extends AbstractErrorController {

	static final Logger logger = LoggerFactory.getLogger(BackendCfMangoApplication.class);

	private static final String PATH = "/error";

	@Autowired
	public MangoErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@RequestMapping(value = PATH)
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
				.including(ErrorAttributeOptions.Include.MESSAGE);
		Map<String, Object> body = getErrorAttributes(request, options);
		HttpStatus status = getStatus(request);
		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	public String getErrorPath() {
		return PATH;
	}

}