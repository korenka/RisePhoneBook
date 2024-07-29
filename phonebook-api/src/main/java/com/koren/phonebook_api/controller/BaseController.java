package com.koren.phonebook_api.controller;

import com.koren.phonebook_api.exception.CustomException;
import com.koren.phonebook_api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        HttpStatus status;

        switch (ex.getErrorType()) {
            case VALIDATION_ERROR:
                status = HttpStatus.BAD_REQUEST;
                break;
            case CONTACT_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case ANOTHER_CUSTOM_ERROR:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }

        errorResponse.setStatus(status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}
