package com.will.taskmanager.exception;

public class CustomException extends RuntimeException{

    public CustomException(Throwable cause) {
        super(cause.getMessage());
    }

    public CustomException(String cause) {
        super(cause);
    }

}
