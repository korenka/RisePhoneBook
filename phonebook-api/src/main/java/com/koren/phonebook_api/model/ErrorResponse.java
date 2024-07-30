package com.koren.phonebook_api.model;

public class ErrorResponse {
    //region members
    private int status;
    private String message;
    //endregion

    //region constructor
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    //endregion

    //region getters setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //endregion
}
