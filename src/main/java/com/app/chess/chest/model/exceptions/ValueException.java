package com.app.chess.chest.model.exceptions;

import org.springframework.http.HttpStatus;

public class ValueException extends RuntimeException {

    public static final String MESSAGE = " level is insufficient ";

    private String message;
    private HttpStatus status;

    public ValueException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ValueException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }

    public ValueException(String message, Throwable cause, String message1, HttpStatus status) {
        super(message, cause);
        this.message = message1;
        this.status = status;
    }

    public ValueException(Throwable cause, String message, HttpStatus status) {
        super(cause);
        this.message = message;
        this.status = status;
    }

    public ValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
        this.status = status;
    }

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
}
