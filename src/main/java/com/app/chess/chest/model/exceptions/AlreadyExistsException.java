package com.app.chess.chest.model.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends RuntimeException {

    public static final String MESSAGE = " already exists";

    private String message;
    private HttpStatus status;

    public static String getMESSAGE() {
        return MESSAGE;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public AlreadyExistsException(String message, HttpStatus status) {

        this.message = message;
        this.status = status;
    }

    public AlreadyExistsException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }

    public AlreadyExistsException(String message, Throwable cause, String message1, HttpStatus status) {
        super(message, cause);
        this.message = message1;
        this.status = status;
    }

    public AlreadyExistsException(Throwable cause, String message, HttpStatus status) {
        super(cause);
        this.message = message;
        this.status = status;
    }

    public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
        this.status = status;
    }
}
