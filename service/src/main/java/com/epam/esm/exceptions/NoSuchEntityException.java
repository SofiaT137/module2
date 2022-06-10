package com.epam.esm.exceptions;

/**
 * NoSuchEntityException class extends RuntimeException presents creating of the NoSuchEntityException exception
 */
public class NoSuchEntityException extends RuntimeException{

    private static final String ERROR_CODE = "404002";

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message) {
         super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
