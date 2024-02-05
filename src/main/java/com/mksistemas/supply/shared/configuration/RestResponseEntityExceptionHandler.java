package com.mksistemas.supply.shared.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mksistemas.supply.shared.domain.BusinessException;
import com.mksistemas.supply.shared.domain.EntityNotFoundException;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
		extends
			ResponseEntityExceptionHandler {

	private final ObjectMapper mapper;

	public RestResponseEntityExceptionHandler(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	private static final String DEFAULT_BUSINESS_FIELDNAME = "business.code";
	private static final String DEFAULT_CONSTRAINT_FIELDNAME = "constraint.code";
	private static final String DEFAULT_ENTITYNOTFOUND_FIELDNAME = "entity.notfound.code";

	@ExceptionHandler(value = {BusinessException.class})
	protected ResponseEntity<Object> handleConflictBusiness(RuntimeException ex,
			WebRequest request) {
		String bodyOfResponse = formatJson(
				new ResponseError(DEFAULT_BUSINESS_FIELDNAME,
						((BusinessException) ex).getCode()));
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = {ConstraintViolationException.class})
	protected ResponseEntity<Object> handleConflictConstraints(
			RuntimeException ex, WebRequest request) {

		String bodyOfResponse = formatJson(
				new ResponseError(DEFAULT_CONSTRAINT_FIELDNAME,
						((ConstraintViolationException) ex).getMessage()));
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = {EntityNotFoundException.class})
	protected ResponseEntity<Object> handleConflictEntityNotFound(
			RuntimeException ex, WebRequest request) {

		String bodyOfResponse = formatJson(new ResponseError(
				DEFAULT_ENTITYNOTFOUND_FIELDNAME, ex.getMessage()));
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
	}

	private String formatJson(ResponseError error) {
		try {
			return mapper.writeValueAsString(error);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	private record ResponseError(String field, @Nullable String value) {
	}

}