package com.epam.esm.validator;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class GiftCertificateValidatorTest {

    @InjectMocks
    private GiftCertificateValidator giftCertificateValidator;

    private static final String NAME_IS_FORBIDDEN = "name is forbidden";
    private static final String DESCRIPTION_IS_TOO_LONG = "description is too long";
    private static final String PRICE_IS_FORBIDDEN = "price is forbidden";
    private static final String DURATION_IS_FORBIDDEN = "duration is forbidden!";
    private static final String CHECK_THE_VALUES = "Check the values";
    private static final String CORRECT_NAME = "Swimming with seals";
    private static final String INCORRECT_NAME = "$52-vmt[****";
    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
    private static final String INCORRECT_DESCRIPTION = new String(new char[451]).replace('\0', ' ');
    private static final Double CORRECT_PRICE = 56.13;
    private static final Double INCORRECT_PRICE = 111111111.9990;
    private static final Integer CORRECT_DURATION = 15;
    private static final Integer INCORRECT_DURATION= 150;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final String date = LocalDateTime.now().format(formatter);
    private static final List<String> list = Arrays.asList("joy","happiness","seal");
    private static final Map<String,String> stringStringMap = new HashMap<>();

    @BeforeAll
    static void init(){
        stringStringMap.put("name_tag","joy");
    }


    @Test
    void validateCorrectGiftCertificate(){
        assertDoesNotThrow(()->giftCertificateValidator
                .validate(new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
    }

    @Test
    void validateInCorrectGiftCertificateName(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(INCORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(NAME_IS_FORBIDDEN));
    }

    @Test
    void validateInCorrectGiftCertificateDescription(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(CORRECT_NAME,INCORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(DESCRIPTION_IS_TOO_LONG));
    }

    @Test
    void validateInCorrectGiftCertificatePrice(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION,INCORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(PRICE_IS_FORBIDDEN));
    }

    @Test
    void validateInCorrectGiftCertificateDuration(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,INCORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(DURATION_IS_FORBIDDEN));
    }

    @Test
    void validateIncorrectMapKeys(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validateMapKeys(stringStringMap));
        assertTrue(thrown.getMessage().contains(CHECK_THE_VALUES));
    }
}



