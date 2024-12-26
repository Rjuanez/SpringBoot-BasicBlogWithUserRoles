package com.tutorial.tutorialspringboot.Exeption;

import com.tutorial.tutorialspringboot.DTO.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

//Clase para manjera excepciones que lleguen

@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception ex, WebRequest request) {
        //En request.getDescription le ponemos false para que no nos de informacion del cliente
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
