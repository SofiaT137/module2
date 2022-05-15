package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ValidatorException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exceptions.ExceptionErrorCode.INCORRECT_TAG_LENGTH;
import static com.epam.esm.exceptions.ExceptionErrorCode.INCORRECT_TAG_NAME;

/**
 * The TagValidator class extents Validator class and provides validation for Tag entity
 */
@Component
public final class TagValidator extends Validator<Tag>{

    private static final String INCORRECT_TAG_LENGTH_EXCEPTION = "thisTagLengthIsForbidden";
    private static final String TAG_NAME_CANNOT_BE_NULL_EXCEPTION = "tagNameCannotBeNull";
    private static final String TAG_NAME = " Tag name: ";

    private static final Integer MIN_TAG_NAME_LENGTH = 2;
    private static final Integer MAX_TAG_NAME_LENGTH = 35;

    @Override
    public void validate(Tag tag){
        validateName(tag.getName());
    }

    private static void validateName(final String name){
        if (name == null){
            throw new ValidatorException(INCORRECT_TAG_LENGTH_EXCEPTION,INCORRECT_TAG_LENGTH);
        }
        if (name.length() < MIN_TAG_NAME_LENGTH || name.length() > MAX_TAG_NAME_LENGTH){
            throw new ValidatorException(INCORRECT_TAG_LENGTH_EXCEPTION,INCORRECT_TAG_LENGTH);
        }
    }
}
