package com.epam.esm.validator;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exceptions.ExceptionErrorCode.*;

/**
 * The GiftCertificateValidator class extents Validator class and provides validation for GiftCertificateDto entity
 */
@Component
public final class GiftCertificateValidator extends Validator<GiftCertificateDto> {

    private static final Integer MIN_GIFT_CERTIFICATE_NAME_LENGTH = 3;
    private static final Integer MAX_GIFT_CERTIFICATE_NAME_LENGTH = 45;
    private static final Integer MAX_GIFT_CERTIFICATE_DESCRIPTION_LENGTH = 450;
    private static final Double MIN_GIFT_CERTIFICATE_PRICE = 0.01;
    private static final Double MAX_GIFT_CERTIFICATE_PRICE = 9999.99;
    private static final Integer MAX_DURATION = 90;
    private static final Integer MIN_DURATION = 1;
    private final List<String> allowedKeys = Arrays.asList("tag_name", "partName", "partDescription", "sortByNameASC", "sortByCreationDateASC", "sortByNameDESC", "sortByCreationDateDESC");

    private static final String TAG_NAME_REGEX = "^[a-zA-Zа-яА-Я\\s'+.-]*$";
    private static final String INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH_EXCEPTION = "This gift certificate length is forbidden!";
    private static final String INCORRECT_GIFT_CERTIFICATE_NAME_EXCEPTION = "This gift certificate name is forbidden!";
    private static final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_EXCEPTION = "This gift certificate description is too long!";
    private static final String INCORRECT_GIFT_CERTIFICATE_PRICE_EXCEPTION = "This gift certificate price is forbidden!";
    private static final String INCORRECT_GIFT_CERTIFICATE_DURATION_EXCEPTION = "This gift certificate duration is forbidden!";
    private static final String INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION = "Check the values that you transferred!";

    @Override
    public void validate(GiftCertificateDto giftCertificate) throws ServiceException {
        if (giftCertificate.getGiftCertificateName() != null) {
            validateName(giftCertificate.getGiftCertificateName());
        }
        if (giftCertificate.getDescription() != null) {
            validateDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            validatePrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null) {
            validateDuration(giftCertificate.getDuration());
        }
    }

    private void validateName(final String name) throws ServiceException {
        if (name.length() < MIN_GIFT_CERTIFICATE_NAME_LENGTH || name.length() > MAX_GIFT_CERTIFICATE_NAME_LENGTH) {
            throw new ServiceException(INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH);
        }
        Pattern namePattern = Pattern.compile(TAG_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        if (!matcher.find()) {
            throw new ServiceException(INCORRECT_GIFT_CERTIFICATE_NAME_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_NAME);
        }
    }

    private void validateDescription(String description) throws ServiceException {
        if (description.length() > MAX_GIFT_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new ServiceException(INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_LENGTH);
        }
    }

    private void validatePrice(Double price) throws ServiceException {
        if (price == null || price < MIN_GIFT_CERTIFICATE_PRICE || price > MAX_GIFT_CERTIFICATE_PRICE) {
            throw new ServiceException(INCORRECT_GIFT_CERTIFICATE_PRICE_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_PRICE);
        }
    }

    private void validateDuration(int duration) throws ServiceException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new ServiceException(INCORRECT_GIFT_CERTIFICATE_DURATION_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_DURATION);
        }
    }

    public void validateMapKeys(Map<String, String> mapWithParameters) throws ServiceException {
        List<String> transferredKeys = new ArrayList<>(mapWithParameters.keySet());
        for (String transferredKey : transferredKeys) {
            if (!allowedKeys.contains(transferredKey)) {
                throw new ServiceException(INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION, INCORRECT_TRANSFERRED_PARAMETERS);
            }
        }
    }
}




