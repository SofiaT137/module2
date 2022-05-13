package com.epam.esm.exceptions;

/**
 * NoPermissionException class extends RuntimeException presents creating of the NoPermissionException exception
 */
public class NoPermissionException extends RuntimeException{

    private String errorCode;

    public NoPermissionException() {
    }
    public NoPermissionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
