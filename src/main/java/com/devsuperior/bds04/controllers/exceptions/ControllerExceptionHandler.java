package com.devsuperior.bds04.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice

public class ControllerExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e,
			HttpServletRequest request){
		
		ValidationError err = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		err.setStatus(status.value());
		err.setTimestamp(Instant.now());
		err.setError("Validação Falhou");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		for(FieldError error : e.getBindingResult().getFieldErrors()) {
			err.addError(error.getField(), error.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);
	}
}
