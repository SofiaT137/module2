package com.epam.esm.exceptions;

/**
 * Class ExceptionEntity helps to create the Exception entity
 */
public class ExceptionEntity {

    private String exceptionMessage;
    private String exceptionCode;

    public ExceptionEntity(String exceptionMessage, String exceptionCode) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String toString() {
        return   "exceptionMessage='" + exceptionMessage + '\'' +
                ", exceptionCode='" + exceptionCode + '\'' +
                '}';
    }
}
