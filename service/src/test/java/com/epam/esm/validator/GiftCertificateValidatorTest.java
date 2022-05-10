package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class GiftCertificateValidatorTest {

    @InjectMocks
    private GiftCertificateValidator giftCertificateValidator;

    private static final String NAME_IS_FORBIDDEN = "thisGiftCertificateNameIsForbidden";
    private static final String DESCRIPTION_IS_TOO_LONG = "hisGiftCertificateDescriptionIsTooLong";
    private static final String PRICE_IS_FORBIDDEN = "thisGiftCertificatePriceIsForbidden";
    private static final String DURATION_IS_FORBIDDEN = "thisGiftCertificateDurationIsForbidden";
    private static final String CHECK_THE_VALUES = "checkTheValuesThatYouTransferred";
    private static final String CORRECT_NAME = "Swimming with seals";
    private static final String INCORRECT_NAME = "$52-vmt[****";
    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
    private static final String INCORRECT_DESCRIPTION = new String(new char[451]).replace('\0', ' ');
    private static final Double CORRECT_PRICE = 56.13;
    private static final Double INCORRECT_PRICE = 111111111.9990;
    private static final Integer CORRECT_DURATION = 15;
    private static final Integer INCORRECT_DURATION= 150;
    private static final LocalDateTime date = LocalDateTime.now();
    private static final List<Tag> list = Arrays.asList(new Tag("joy"),new Tag("happiness"),new Tag("seal"));
    private static final MultiValueMap<String,String> stringStringMap = new LinkedMultiValueMap<>();

    @BeforeAll
    static void init(){
        stringStringMap.set("name_tag","joy");
    }


    @Test
    void validateCorrectGiftCertificate(){
        assertDoesNotThrow(()->giftCertificateValidator
                .validate(new GiftCertificate(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
    }

    @Test
    void validateInCorrectGiftCertificateName(){
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> giftCertificateValidator.validate(new GiftCertificate(INCORRECT_NAME,
                        CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(NAME_IS_FORBIDDEN));
    }

    @Test
    void validateInCorrectGiftCertificateDescription(){
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> giftCertificateValidator.validate(new GiftCertificate(CORRECT_NAME,INCORRECT_DESCRIPTION,
                        CORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(DESCRIPTION_IS_TOO_LONG));
    }

    @Test
    void validateInCorrectGiftCertificatePrice(){
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> giftCertificateValidator.validate(new GiftCertificate(CORRECT_NAME,CORRECT_DESCRIPTION,
                        INCORRECT_PRICE,CORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(PRICE_IS_FORBIDDEN));
    }

    @Test
    void validateInCorrectGiftCertificateDuration(){
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> giftCertificateValidator.validate(new GiftCertificate(CORRECT_NAME,CORRECT_DESCRIPTION,
                        CORRECT_PRICE,INCORRECT_DURATION,date,date,list)));
        assertTrue(thrown.getMessage().contains(DURATION_IS_FORBIDDEN));
    }

    @Test
    void validateIncorrectMapKeys(){
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> giftCertificateValidator.validateMapKeys(stringStringMap));
        assertTrue(thrown.getMessage().contains(CHECK_THE_VALUES));
    }
}



