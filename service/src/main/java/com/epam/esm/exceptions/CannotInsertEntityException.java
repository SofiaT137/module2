package com.epam.esm.exceptions;

/**
 * CannotInsertEntityException class extends RuntimeException and presents creating
 * of the CannotInsertEntityException exception
 */
public class CannotInsertEntityException extends RuntimeException{

    private static final String ERROR_CODE = "404002";

    public CannotInsertEntityException() {
    }
    public CannotInsertEntityException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
