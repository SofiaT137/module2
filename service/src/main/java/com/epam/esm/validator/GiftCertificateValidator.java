package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionErrorCode.*;

/**
 * The GiftCertificateValidator class extents Validator class and provides validation for GiftCertificateDto entity
 */
@Component
public final class GiftCertificateValidator extends Validator<GiftCertificate> {

    private static final Integer MIN_GIFT_CERTIFICATE_NAME_LENGTH = 3;
    private static final Integer MAX_GIFT_CERTIFICATE_NAME_LENGTH = 50;
    private static final Integer MAX_GIFT_CERTIFICATE_DESCRIPTION_LENGTH = 450;
    private static final Double MIN_GIFT_CERTIFICATE_PRICE = 0.01;
    private static final Double MAX_GIFT_CERTIFICATE_PRICE = 9999.99;
    private static final Integer MAX_DURATION = 90;
    private static final Integer MIN_DURATION = 1;
    private final List<String> allowedKeys = Arrays.asList("tag_name", "partName", "partDescription", "sortByName",
            "sortByCreationDate");

    private static final String INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH_EXCEPTION = "giftCertificateLengthIsForbidden";
    private static final String GIFT_CERTIFICATE_NAME_CANNOT_BE_NULL_EXCEPTION = "giftCertificateNameCannotBeNull";
    private static final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_EXCEPTION = "giftCertificateDescriptionIsTooLong";
    private static final String INCORRECT_GIFT_CERTIFICATE_PRICE_EXCEPTION = "giftCertificatePriceIsForbidden";
    private static final String GIFT_CERTIFICATE_PRICE_CANNOT_BE_NULL_EXCEPTION = "giftCertificatePriceCannotBeNull";
    private static final String INCORRECT_GIFT_CERTIFICATE_DURATION_EXCEPTION = "giftCertificateDurationIsForbidden";
    private static final String GIFT_CERTIFICATE_DURATION_CANNOT_BE_NULL_EXCEPTION = "giftCertificateDurationCannotBeNull";
    private static final String INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION = "checkTheValuesThatYouTransferred";

    @Override
    public void validate(GiftCertificate giftCertificate) {
        validateName(giftCertificate.getGiftCertificateName());
        if (giftCertificate.getDescription() != null) {
            validateDescription(giftCertificate.getDescription());
        }
        validatePrice(giftCertificate.getPrice());
        validateDuration(giftCertificate.getDuration());
    }

    private void validateName(final String name){
        if (name == null){
            throw new ValidatorException(GIFT_CERTIFICATE_NAME_CANNOT_BE_NULL_EXCEPTION,
                    INCORRECT_GIFT_CERTIFICATE_NAME);
        }
        if (name.length() < MIN_GIFT_CERTIFICATE_NAME_LENGTH || name.length() > MAX_GIFT_CERTIFICATE_NAME_LENGTH) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH_EXCEPTION,
                    INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH);
        }
    }

    private void validateDescription(String description){
        if (description.length() > MAX_GIFT_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_EXCEPTION,
                    INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_LENGTH);
        }
    }

    private void validatePrice(Double price){
        if (price == null){
            throw new ValidatorException(GIFT_CERTIFICATE_PRICE_CANNOT_BE_NULL_EXCEPTION,
                    INCORRECT_GIFT_CERTIFICATE_PRICE);
        }
        if (price < MIN_GIFT_CERTIFICATE_PRICE || price > MAX_GIFT_CERTIFICATE_PRICE) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_PRICE_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_PRICE);
        }
    }

    public void validateDuration(Integer duration){
        if (duration == null){
            throw new ValidatorException(GIFT_CERTIFICATE_DURATION_CANNOT_BE_NULL_EXCEPTION,
                    INCORRECT_GIFT_CERTIFICATE_DURATION);
        }
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_DURATION_EXCEPTION,
                    INCORRECT_GIFT_CERTIFICATE_DURATION);
        }
    }

    public void validateMapKeys(MultiValueMap<String, String> mapWithParameters){
        List<String> transferredKeys = new ArrayList<>(mapWithParameters.keySet());
        for (String transferredKey : transferredKeys) {
            if (!allowedKeys.contains(transferredKey)) {
                throw new ValidatorException(INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION,
                        INCORRECT_TRANSFERRED_PARAMETERS);
            }
        }
    }
}




