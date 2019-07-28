package com.citytechware.idmanager.webservices.dto;

import lombok.Data;

@Data
public class ErrorMessage {
    private String status;
    private String message;

    private ErrorMessage() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    public ErrorMessage(String message) {
        this.status = "Error";
        this.message = message;
    }
}
