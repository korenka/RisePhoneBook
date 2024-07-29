package com.koren.phonebook_api.exception;

public class CustomException extends RuntimeException {
    private final ErrorType errorType;

    public CustomException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
