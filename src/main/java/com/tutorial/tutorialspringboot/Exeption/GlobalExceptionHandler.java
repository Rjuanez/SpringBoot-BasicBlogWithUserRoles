package com.tutorial.tutorialspringboot.Exeption;

import com.tutorial.tutorialspringboot.DTO.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Clase para manjera excepciones que lleguen

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        //En request.getDescription le ponemos false para que no nos de informacion del cliente
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetail> handleBlogAppException(BlogAppException ex, WebRequest request) {
        //En request.getDescription le ponemos false para que no nos de informacion del cliente
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail> handleAccesDeniedException(Exception ex, WebRequest request) {
        //En request.getDescription le ponemos false para que no nos de informacion del cliente
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception ex, WebRequest request) {
        //En request.getDescription le ponemos false para que no nos de informacion del cliente
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
