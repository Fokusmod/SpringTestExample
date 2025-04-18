package com.example.SpringTestExample.dto;

public class OperationMessage {
    private String message;

    public OperationMessage(String message) {
        this.message = message;
    }

    public OperationMessage() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
