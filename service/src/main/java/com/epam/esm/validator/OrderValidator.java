package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ValidatorException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionErrorCode.ORDERS_GIFT_CERTIFICATE_LIST_CANNOT_BE_NULL;
import static com.epam.esm.exceptions.ExceptionErrorCode.ORDERS_USER_CANNOT_BE_NULL;

@Component
public final class OrderValidator extends Validator<Order> {

    private static final String CERTIFICATE_LIST_IS_NULL_EXCEPTION_MESSAGE = "giftCertificateListCannotBeNull";
    private static final String USER_IS_NULL_EXCEPTION_MESSAGE = "userCannotBeNull";

    @Override
    public void validate(Order order) throws ValidatorException {
        validateOrdersUser(order.getUser());
        validateOrdersGiftCertificateList(order.getGiftCertificateList());
    }

    private void validateOrdersGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        if (giftCertificateList.isEmpty()){
            throw new ValidatorException(CERTIFICATE_LIST_IS_NULL_EXCEPTION_MESSAGE,
                    ORDERS_GIFT_CERTIFICATE_LIST_CANNOT_BE_NULL);
        }
    }

    private void validateOrdersUser(User user) {
        if (user == null){
            throw new ValidatorException(USER_IS_NULL_EXCEPTION_MESSAGE,
                    ORDERS_USER_CANNOT_BE_NULL);
        }
    }

}
