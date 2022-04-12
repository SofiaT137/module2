package com.epam.esm.validator;

import com.epam.esm.exceptions.ServiceException;
import static com.epam.esm.exceptions.ExceptionErrorCode.INCORRECT_ID;

public class Validator {

    private static final int MIN_ID = 1;
    private static final String INCORRECT_ID_EXCEPTION_MESSAGE = "This id is incorrect!";

    public void validateId(long id) throws ServiceException {
        if (id < MIN_ID) {
            throw new ServiceException(INCORRECT_ID_EXCEPTION_MESSAGE,INCORRECT_ID);
        }
    }

}
