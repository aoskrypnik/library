package com.ukma.library.handler;

import com.ukma.library.exception.AuthenticationException;
import com.ukma.library.exception.IsbnNotMatchException;
import com.ukma.library.exception.NoCopiesAvailableException;
import com.ukma.library.exception.PasswordNotMatchException;
import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler({AuthenticationException.class, PasswordNotMatchException.class,
			UserAlreadyExistsException.class, NoCopiesAvailableException.class})
	public void handleConflict(HttpServletResponse response) throws IOException {
		response.sendError(CONFLICT.value());
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public void handleNotFound(HttpServletResponse response) throws IOException {
		response.sendError(NOT_FOUND.value());
	}

	@ExceptionHandler({IsbnNotMatchException.class})
	public void handleBadRequest(HttpServletResponse response) throws IOException {
		response.sendError(BAD_REQUEST.value());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatus status,
																  WebRequest request) {
		log.warn(ex.getMessage());
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + " " + e.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
}
