package com.epam.esm.validator;

import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ValidatorException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exceptions.ExceptionErrorCode.INCORRECT_TAG_LENGTH;

@Component
public final class UserValidator extends Validator<User> {

    private static final String INCORRECT_USER_LENGTH_EXCEPTION = "thisUserNameLengthIsForbidden!";
    private static final String USER_NAME = " User name: ";

    private static final Integer MIN_TAG_NAME_LENGTH = 4;
    private static final Integer MAX_TAG_NAME_LENGTH = 30;

    @Override
    public void validate(User user) throws ValidatorException {
        validateName(user.getName());
    }

    private static void validateName(final String name) throws ValidatorException {
        if (name.length() < MIN_TAG_NAME_LENGTH || name.length() > MAX_TAG_NAME_LENGTH){
            throw new ValidatorException(INCORRECT_USER_LENGTH_EXCEPTION,INCORRECT_TAG_LENGTH);
        }
   }
}
