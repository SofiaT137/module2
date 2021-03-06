package com.epam.esm.exceptions;

/**
 * IncorrectTransferredParametersException class extends RuntimeException presents creating
 * of the IncorrectTransferredParametersException exception
 */
public class IncorrectTransferredParametersException extends RuntimeException {

    private static final String ERROR_CODE = "403002";

    public IncorrectTransferredParametersException() {
    }
    public IncorrectTransferredParametersException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}


