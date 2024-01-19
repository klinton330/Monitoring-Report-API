package com.pointel.monitor.exception;

import java.time.format.DateTimeParseException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pointel.monitor.domain.HttpResponse;

@ControllerAdvice
public class ExceptionHandlers implements ErrorController {

	private static final String DATA_ALREADY_FOUND = "Data is already entered in system";
	private static final String DATA_NOT_FOUND = "Data Not Found in System";
	private static final String NO_PROPER_INPUT = "No proper input given";

	@ExceptionHandler(DataAlreadyFoundException.class)
	public ResponseEntity<HttpResponse> dataAlreadyFound() {
		return createHttpExceptionResponse(HttpStatus.BAD_REQUEST, DATA_ALREADY_FOUND);
	}

	public ResponseEntity<HttpResponse> createHttpExceptionResponse(HttpStatus httpStatus, String message) {
		HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
		return new ResponseEntity<HttpResponse>(httpResponse, httpStatus);

	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<HttpResponse> dataNotFound() {
		return createHttpExceptionResponse(HttpStatus.BAD_REQUEST, DATA_NOT_FOUND);
	}

	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<HttpResponse> dateparseException() {
		return createHttpExceptionResponse(HttpStatus.NOT_FOUND, NO_PROPER_INPUT);
	}
}
