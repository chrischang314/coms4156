package com.tabsnotspaces.match;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Map<String, String[]>> handleConstraintViolation(ConstraintViolationException ex) {
        String[] errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toArray(String[]::new);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String[]>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String[] errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toArray(String[]::new);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, String[]> getErrorsMap(String... errors) {
        Map<String, String[]> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
