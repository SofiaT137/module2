package com.epam.esm.validator;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Spy
    private TagValidator tagValidator = Mockito.mock(TagValidator.class);


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
    private static Map<String,String> stringStringMap = new HashMap<>();



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
        assertTrue(thrown.getMessage().contains("name is forbidden"));
    }

    @Test
    void validateInCorrectGiftCertificateDescription(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(CORRECT_NAME,INCORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains("description is too long"));
    }

    @Test
    void validateInCorrectGiftCertificatePrice(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION,INCORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains("price is forbidden"));
    }

    @Test
    void validateInCorrectGiftCertificateDuration(){
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validate(new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,INCORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains("duration is forbidden!"));
    }

    @Test
    void validateIncorrectMapKeys(){
        stringStringMap.put("name_tag","joy");
        ServiceException thrown = assertThrows(
                ServiceException.class,
                () -> giftCertificateValidator.validateMapKeys(stringStringMap));
        assertTrue(thrown.getMessage().contains("Check the values"));
    }
}



