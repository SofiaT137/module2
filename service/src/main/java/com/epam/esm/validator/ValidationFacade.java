package com.epam.esm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * ValidationFacade is a utility class that assists in adding validation
 */
@Component
public class ValidationFacade {

    private final Validator validator;

    @Autowired
    public ValidationFacade(Validator validator) {
        this.validator = validator;
    }

    /**
     * Method validate helps to validate the object
     * @param object T object
     * @param groups Class<?>... groups
     * @param <T> T object
     */
    public <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(object, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}