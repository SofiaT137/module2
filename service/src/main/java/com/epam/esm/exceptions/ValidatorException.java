package com.epam.esm.exceptions;

/**
 * ValidatorException class extends RuntimeException presents creating of the ValidatorException exception
 */
public class ValidatorException extends RuntimeException {

    private String errorCode;

    public ValidatorException() {
    }

    public ValidatorException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
