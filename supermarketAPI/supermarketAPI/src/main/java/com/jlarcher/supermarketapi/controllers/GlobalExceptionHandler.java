package com.jlarcher.supermarketapi.controllers;


import com.jlarcher.supermarketapi.exceptions.ProductNotFoundException;
import com.jlarcher.supermarketapi.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException exception) {
        log.error("Entity not found: ", exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        String errorMessage = "Producto no encontrado con el ID provisto";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        log.error("Validation errors: {}", errors);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(String.join(", ", errors));
        errorResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        log.error("IllegalArgumentException: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Error en la lógica de negocio: " +exception.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        log.error("Error en la lógica de negocio: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {

        ErrorResponse errorResponse = new ErrorResponse();
        String errorMessage = "Ocurrió un error inesperado. Por favor, contacta al soporte si el problema persiste.";

        errorResponse.setMessage(errorMessage);
        errorResponse.setTimeStamp(LocalDateTime.now());
        log.error("Ocurrió un error inesperado. Por favor, contacta al soporte si el problema persiste.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
