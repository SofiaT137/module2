package com.epam.esm.exceptions;

/**
 * NoPermissionException class extends RuntimeException presents creating of the NoPermissionException exception
 */
public class NoPermissionException extends RuntimeException{

    private static final String ERROR_CODE = "403000";

    public NoPermissionException() {
    }
    public NoPermissionException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
