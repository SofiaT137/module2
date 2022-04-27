package com.epam.esm.validator;

import com.epam.esm.exceptions.ValidatorException;
import static com.epam.esm.exceptions.ExceptionErrorCode.INCORRECT_ID;

/**
 * The validator class
 */
public abstract class Validator<T> {

    private static final Long MIN_ID = 1L;
    private static final String WRONG_ID_MESSAGE = "Check this id!";

    /**
     * The method checkID checks transferred long id value
     * @param id Long id
     */
   public void checkID(Long id) throws ValidatorException {
        if (id < MIN_ID){
            throw new ValidatorException(WRONG_ID_MESSAGE,INCORRECT_ID);
        }
    }
    /**
     * The validate method helps validate transferred abstractEntity
     * @param abstractEntity T abstractEntity
     */
    public abstract void validate(T abstractEntity) throws ValidatorException;

}
