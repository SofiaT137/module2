package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ValidatorException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exceptions.ExceptionErrorCode.*;

/**
 * The TagValidator class extents Validator class and provides validation for Tag entity
 */
@Component
public final class TagValidator extends Validator<Tag>{

    private static final String TAG_NAME_REGEX = "^[a-zA-Zа-яА-Я\\s'+.-]*$";

    private static final String INCORRECT_TAG_LENGTH_EXCEPTION = "This tag length is forbidden!";
    private static final String INCORRECT_TAG_NAME_EXCEPTION = "This tag name is forbidden!";

    private static final Integer MIN_TAG_NAME_LENGTH = 2;
    private static final Integer MAX_TAG_NAME_LENGTH = 30;

    @Override
    public void validate(Tag tag) throws ValidatorException {
        validateName(tag.getName());
    }

    private static void validateName(final String name) throws ValidatorException {
        if (name.length() < MIN_TAG_NAME_LENGTH || name.length() > MAX_TAG_NAME_LENGTH){
            throw new ValidatorException(INCORRECT_TAG_LENGTH_EXCEPTION,INCORRECT_TAG_LENGTH);
        }
        Pattern namePattern = Pattern.compile(TAG_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        if(!matcher.find()){
            throw new ValidatorException(INCORRECT_TAG_NAME_EXCEPTION,INCORRECT_TAG_NAME);
        }
    }
}
