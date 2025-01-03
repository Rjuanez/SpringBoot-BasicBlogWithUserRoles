package com.tutorial.tutorialspringboot.Exeption;

import org.springframework.http.HttpStatus;

public class BlogAppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String message;

    public BlogAppException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAppException(String message, HttpStatus status, String message1) {
        super();
        this.status = status;
        this.message = message;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
