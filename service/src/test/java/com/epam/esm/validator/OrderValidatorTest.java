package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

    @InjectMocks
    private OrderValidator orderValidator;

    private static final String CERTIFICATE_LIST_IS_NULL_EXCEPTION_MESSAGE = "giftCertificateListCannotBeNull";
    private static final String USER_IS_NULL_EXCEPTION_MESSAGE = "userCannotBeNull";

    private static List<GiftCertificate> emptyGiftCertificateList = new ArrayList<>();
    private static GiftCertificate giftCertificate = new GiftCertificate("aaa",
            "aaa", 70.12, 5, LocalDateTime.now(), LocalDateTime.now(),
            Collections.singletonList(new Tag("joy")));
    private static final User user = new User(1L,"AlexRendal","cccc");
    private static final LocalDateTime data = LocalDateTime.now();
    private static final Double price = giftCertificate.getPrice();
    private static final Order invalidateOrderWithEmptyCertificateList = new Order();
    private static final Order invalidateOrderWithEmptyUser = new Order();

    @BeforeAll
    static void init(){
        invalidateOrderWithEmptyCertificateList.setPrice(price);
        invalidateOrderWithEmptyCertificateList.setPurchaseTime(data);
        invalidateOrderWithEmptyCertificateList.addUserToOrder(user);
        invalidateOrderWithEmptyUser.setPrice(price);
        invalidateOrderWithEmptyUser.setPurchaseTime(data);
        invalidateOrderWithEmptyUser.addGiftCertificateToOrder(giftCertificate);
    }

    @Test
    void validateIncorrectOrderWithEmptyCertificateList() {
        ValidatorException thrown = assertThrows(ValidatorException.class,
                () -> orderValidator.validate(invalidateOrderWithEmptyCertificateList));
        assertTrue(thrown.getMessage().contains(CERTIFICATE_LIST_IS_NULL_EXCEPTION_MESSAGE));
    }

    @Test
    void validateIncorrectOrderWithEmptyUser() {
        ValidatorException thrown = assertThrows(ValidatorException.class,
                () -> orderValidator.validate(invalidateOrderWithEmptyUser));
        assertTrue(thrown.getMessage().contains(USER_IS_NULL_EXCEPTION_MESSAGE));
    }
}