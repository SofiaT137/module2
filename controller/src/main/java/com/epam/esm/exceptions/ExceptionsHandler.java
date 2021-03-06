package com.epam.esm.exceptions;

import com.epam.esm.internalization.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;


/**
 * ExceptionsHandler class allows intercept and process exceptions across the project
 */
@RestControllerAdvice
public class ExceptionsHandler {

    private final Translation translation;

    @Autowired
    public ExceptionsHandler(Translation translation) {
        this.translation = translation;
    }

    private static final String STRING_MESSAGE = "Message: ";
    private static final String BAD_VALIDATION_RESULT_EXCEPTION_MESSAGE = "403001";
    private static final String ACCESS_DENIED_EXCEPTION_MESSAGE = "403000";
    private static final String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "404000";


    /**
     * Method resourceNotFoundException handles the NoSuchEntityException exception
     * @param exception NoSuchEntityException exception
     * @return Response entity with ExceptionEntity entity and HttpStatus "NOT_FOUND"
     */
    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> resourceNotFoundException(NoSuchEntityException exception) {
        String exceptionMessage = translation.translate(exception.getMessage());
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_FOUND);
    }

    /**
     * Method noPermissionException handles the NoPermissionException exception
     * @param exception NoPermissionException exception
     * @return Response entity with ExceptionEntity entity and HttpStatus "BAD_REQUEST"
     */
    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<Object> noPermissionException(NoPermissionException exception) {
        String exceptionMessage = translation.translate(exception.getMessage());
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method cannotInsertEntityException handles the CannotInsertEntityException exception
     * @param exception CannotInsertEntityException exception
     * @return Response entity with ExceptionEntity entity and HttpStatus "BAD_REQUEST"
     */
    @ExceptionHandler(CannotInsertEntityException.class)
    public ResponseEntity<Object> cannotInsertEntityException(CannotInsertEntityException exception) {
        String exceptionMessage = translation.translate(exception.getMessage());
        String exceptionCode = exception.getErrorCode();
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,exceptionCode);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method badRequestException handles the ValidatorException exception
     * @param exception ValidatorException exception
     * @return Response entity with ExceptionEntity entity and HttpStatus "BAD_REQUEST"
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> badRequestException(ConstraintViolationException exception) {
        String exceptionMessage = translation.translate(exception.getMessage());
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage, BAD_VALIDATION_RESULT_EXCEPTION_MESSAGE);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method wrongParametersException handles the MethodArgumentTypeMismatchException exception
     * @param exception MethodArgumentTypeMismatchException exception
     * @return Response entity with exception message and HttpStatus "BAD_REQUEST"
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> wrongParametersException(MethodArgumentTypeMismatchException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method badCredentialsException handles the BadCredentialsException exception
     * @param exception BadCredentialsException exception
     * @return Response entity with exception message and HttpStatus "NOT_FOUND"
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsException(BadCredentialsException exception) {
        String exceptionMessage = translation.translate(exception.getLocalizedMessage());
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,BAD_CREDENTIALS_EXCEPTION_MESSAGE);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_FOUND);
    }

    /**
     * Method accessDeniedExceptionException handles the AccessDeniedException exception
     * @param exception AccessDeniedException exception
     * @return Response entity with exception message and HttpStatus "FORBIDDEN"
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionException(AccessDeniedException exception) {
        String exceptionMessage = translation.translate(exception.getLocalizedMessage());
        ExceptionEntity exceptionEntity = new ExceptionEntity(exceptionMessage,ACCESS_DENIED_EXCEPTION_MESSAGE);
        return new ResponseEntity<>(exceptionEntity, HttpStatus.FORBIDDEN);
    }

    /**
     * Method requestMethodNotSupportedException handles the HttpRequestMethodNotSupportedException exception
     * @param exception HttpRequestMethodNotSupportedException exception
     * @return Response entity with exception message and HttpStatus "METHOD_NOT_ALLOWED"
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * Method noHandlerFoundException handles the NoHandlerFoundException exception
     * @param exception NoHandlerFoundException exception
     * @return Response entity with exception message and HttpStatus "BAD_REQUEST"
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method internalServerErrorException handles the Exception exception
     * @param exception Exception exception
     * @return Response entity with exception message and HttpStatus "INTERNAL_SERVER_ERROR"
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception exception) {
        String exceptionMessage = exception.getMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
