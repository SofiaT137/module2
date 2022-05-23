package com.epam.esm.validator;

import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

//    @InjectMocks
//    private UserValidator userValidator;
//
//    private static final String CORRECT_USER_NAME = "Dima";
//    private static final String INCORRECT_USER_NAME = "D7^";
//    private static final String INCORRECT_USER_NAME_EXCEPTION = "thisUserNameLengthIsForbidden!";
//
//    private static User correctUser;
//    private static User incorrectUser;
//
//    @BeforeAll
//    static void init(){
//        correctUser = new User(CORRECT_USER_NAME);
//        incorrectUser = new User(INCORRECT_USER_NAME);
//    }
//
//    @Test
//    void validateCorrectUserNameLength() {
//        assertDoesNotThrow(()->userValidator
//                .validate(correctUser));
//    }
//
//    @Test
//    void validateIncorrectUserNameLength() {
//        ValidatorException thrown = assertThrows(ValidatorException.class,
//                () -> userValidator.validate(incorrectUser));
//        assertTrue(thrown.getMessage().contains(INCORRECT_USER_NAME_EXCEPTION));
//    }
}