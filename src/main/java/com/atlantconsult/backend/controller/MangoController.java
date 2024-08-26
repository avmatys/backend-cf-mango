package com.atlantconsult.backend.controller;

import java.util.Collections;

import javax.naming.ConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.atlantconsult.backend.BackendCfMangoApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MangoController {

	static final Logger logger = LoggerFactory.getLogger(BackendCfMangoApplication.class);

	@Autowired
	private Environment env;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/mango/analytics/greetings")
	public String getGreetings(@RequestParam(required = false) String name) {
		String greetings = name != null ? "Hello, " + name : "Hello!";
		return greetings;
	}

	@GetMapping("/mango/analytics/webhook")
	public ResponseEntity<MangoWebhook> getWebhook(@RequestParam(required = false) String gaCid,
			@RequestParam(required = false) String yaCid, @RequestParam(required = true) String callerNumber)
			throws IllegalArgumentException, ConfigurationException, RestClientException {

		// Validate incoming values - at least one should exists
		if ((gaCid == null || gaCid.length() == 0) && (yaCid == null || yaCid.length() == 0)) {
			throw new IllegalArgumentException(
					"Required request parameter 'yaCid' or 'gaCid' for method parameter type String is not present");
		}

		// Get values from properties
		String cpiUrl = env.getProperty("cpi.baseUrl");
		String path = env.getProperty("cpi.accountDataPath");
		String username = env.getProperty("cpi.username");
		String password = env.getProperty("cpi.password");

		MangoWebhook webhook = new MangoWebhook();
		if (cpiUrl != null && path != null && username != null && password != null) {

			// Form url
			String url = cpiUrl + path;

			// Create new webhook
			webhook = new MangoWebhook(callerNumber, gaCid, yaCid);

			// Transform to JSON
			String payload = "";
			try {
				payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(webhook);
			} catch (JsonProcessingException e) {
				logger.error("Conversion failed: Webhook object to Json is not coverted", e);
			}

			// Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Basic authentication
			headers.setBasicAuth(username, password);

			// Build the request
			HttpEntity<?> request = new HttpEntity<>(payload, headers);

			// Send POST request
			ResponseEntity<String> response = new RestTemplate().postForEntity(url, request, String.class);

			// Check response
			if (!response.getStatusCode().is2xxSuccessful()) {
				logger.error("Login failed: data is not sent to CPI", response.getBody());
			}

		} else {
			throw new ConfigurationException("Mandatory config data was not fount");
		}

		return new ResponseEntity<MangoWebhook>(webhook, HttpStatus.OK);
	}
}
