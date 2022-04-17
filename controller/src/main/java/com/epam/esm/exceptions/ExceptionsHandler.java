package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class ExceptionsHandler {

    private static final String STRING_MESSAGE = "Message: ";

    //обработка dao ошибок
    @ExceptionHandler(DaoException.class)
    public ResponseEntity<Object> resourceNotFoundException(DaoException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_FOUND);
    }

    //обработка service ошибок
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> badRequestException(ServiceException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.BAD_REQUEST);
    }

    //переданы неверные аргументы
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> wrongParametersException(MethodArgumentTypeMismatchException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    //не разрешено так делать(обработка 405 ошибки)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    //не нашел нужного обработчика
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    //Internal Server error exception 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
