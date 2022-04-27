package com.epam.esm.exceptions;


/**
 * DaoException class presents creating of the DaoException entity
 */
public class DaoException extends RuntimeException{

    private String errorCode;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException (String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
