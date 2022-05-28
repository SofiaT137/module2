package com.epam.esm.exceptions;

public class IncorrectTransferredParametersException extends RuntimeException {

    private static final String ERROR_CODE = "403003";

    public IncorrectTransferredParametersException() {
    }
    public IncorrectTransferredParametersException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}


