package com.koren.phonebook_api.exception;

public class CustomException extends RuntimeException {
    //region members
    private final ErrorType errorType;
    //enfregion

    //region public methods
    public CustomException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
    //endregion
}
