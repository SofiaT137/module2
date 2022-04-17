package com.epam.esm.validator;

import com.epam.esm.exceptions.ServiceException;
import static com.epam.esm.exceptions.ExceptionErrorCode.INCORRECT_ID;

public abstract class Validator {

    private final Long MIN_ID = 1L;
    private final String WRONG_ID_MESSAGE = "Check this id!";

   public void checkID(Long id) throws ServiceException {
        if (id < MIN_ID){
            throw new ServiceException(WRONG_ID_MESSAGE,INCORRECT_ID);
        }
    }

}
