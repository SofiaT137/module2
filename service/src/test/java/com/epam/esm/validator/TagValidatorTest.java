package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class TagValidatorTest {

    @InjectMocks
    private TagValidator tagValidator;

    private static final Long CORRECT_ID = 9L;
    private static final Long INCORRECT_ID = -9L;
    private static final String CORRECT_NAME = "happiness";
    private static final String INCORRECT_NAME = "$52-vmt[****";
    @Test
    void validateIncorrectId() {
        assertThrows(ServiceException.class,()-> tagValidator.checkID(INCORRECT_ID));
    }

    @Test
    void validateCorrectId(){
        assertDoesNotThrow(()->tagValidator.checkID(CORRECT_ID));
    }

    @Test
    void validateIncorrectName() {
        assertThrows(ServiceException.class,()->tagValidator.validate(new Tag(INCORRECT_NAME)));
    }

    @Test
    void validateCorrectName() {
        assertDoesNotThrow(()->tagValidator.validate(new Tag(CORRECT_NAME)));
    }
}