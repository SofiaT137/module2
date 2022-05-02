package com.epam.esm.exceptions;

import java.util.Optional;

public class CannotInsertEntityException extends RuntimeException{

    private String errorCode;

    public CannotInsertEntityException() {
    }
    public CannotInsertEntityException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
