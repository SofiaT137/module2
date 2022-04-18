package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * ExceptionsHandler class allows intercept and process exceptions across the project
 */
@RestControllerAdvice
public class ExceptionsHandler {

    private static final String STRING_MESSAGE = "Message: ";

    /**
     * Method resourceNotFoundException helps intercept and process DaoException exceptions
     * @param exception DaoException exception
     * @return new ResponseEntity<>(Exception entity, HttpStatus);
     */
    @ExceptionHandler(DaoException.class)
    public ResponseEntity<Object> resourceNotFoundException(DaoException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_FOUND);
    }

    /**
     * Method resourceNotFoundException helps intercept and process Service exceptions
     * @param exception Service exception
     * @return new ResponseEntity<>(Exception entity, HttpStatus);
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> badRequestException(ServiceException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method resourceNotFoundException helps intercept and process MethodArgumentTypeMismatchException exceptions
     * @param exception MethodArgumentTypeMismatchException exception
     * @return new ResponseEntity<>(Exception entity, HttpStatus);
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> wrongParametersException(MethodArgumentTypeMismatchException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method resourceNotFoundException helps intercept and process HttpRequestMethodNotSupportedException exceptions
     * @param exception HttpRequestMethodNotSupportedException exception
     * @return new ResponseEntity<>(Exception entity, HttpStatus);
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Method resourceNotFoundException helps intercept and process NoHandlerFoundException exceptions
     * @param exception NoHandlerFoundException exception
     * @return new ResponseEntity<>(Exception entity, HttpStatus);
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method resourceNotFoundException helps intercept and process InternalServerException exceptions
     * @param exception Exception exception
     * @return new ResponseEntity<>(Exception entity, HttpStatus);
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
