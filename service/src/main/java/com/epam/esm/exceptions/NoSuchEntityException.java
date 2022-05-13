package com.epam.esm.exceptions;

/**
 * NoSuchEntityException class extends RuntimeException presents creating of the NoSuchEntityException exception
 */
public class NoSuchEntityException extends RuntimeException{

    private String errorCode;

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message, String errorCode) {
         super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
