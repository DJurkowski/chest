package com.app.chess.chest.model.exceptions;

import org.springframework.http.HttpStatus;

public class TourStatusException extends RuntimeException {

    public static final String MESSAGE = " stopped recruiting ";

    private String message;
    private HttpStatus status;

    public TourStatusException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public TourStatusException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }

    public TourStatusException(String message, Throwable cause, String message1, HttpStatus status) {
        super(message, cause);
        this.message = message1;
        this.status = status;
    }

    public TourStatusException(Throwable cause, String message, HttpStatus status) {
        super(cause);
        this.message = message;
        this.status = status;
    }

    public TourStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1, HttpStatus status) {
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
