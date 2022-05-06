package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exceptions.ExceptionErrorCode.*;

/**
 * The GiftCertificateValidator class extents Validator class and provides validation for GiftCertificateDto entity
 */
@Component
public final class GiftCertificateValidator extends Validator<GiftCertificate> {

    private static final Integer MIN_GIFT_CERTIFICATE_NAME_LENGTH = 3;
    private static final Integer MAX_GIFT_CERTIFICATE_NAME_LENGTH = 45;
    private static final Integer MAX_GIFT_CERTIFICATE_DESCRIPTION_LENGTH = 450;
    private static final Double MIN_GIFT_CERTIFICATE_PRICE = 0.01;
    private static final Double MAX_GIFT_CERTIFICATE_PRICE = 9999.99;
    private static final Integer MAX_DURATION = 90;
    private static final Integer MIN_DURATION = 1;
    private final List<String> allowedKeys = Arrays.asList("tag_name", "partName", "partDescription", "sortByNameASC", "sortByCreationDateASC", "sortByNameDESC", "sortByCreationDateDESC");

    private static final String TAG_NAME_REGEX = "^[a-zA-Zа-яА-Я\\s'+.-]*$";
    private static final String INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH_EXCEPTION = "thisGiftCertificateLengthIsForbidden!";
    private static final String INCORRECT_GIFT_CERTIFICATE_NAME_EXCEPTION = "thisGiftCertificateNameIsForbidden!";
    private static final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_EXCEPTION = "thisGiftCertificateDescriptionIsTooLong!";
    private static final String INCORRECT_GIFT_CERTIFICATE_PRICE_EXCEPTION = "thisGiftCertificatePriceIsForbidden!";
    private static final String INCORRECT_GIFT_CERTIFICATE_DURATION_EXCEPTION = "thisGiftCertificateDurationIsForbidden!";
    private static final String INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION = "checkTheValuesThatYouTransferred!";

    @Override
    public void validate(GiftCertificate giftCertificate) throws ValidatorException {
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

    private void validateName(final String name) throws ValidatorException {
        if (name.length() < MIN_GIFT_CERTIFICATE_NAME_LENGTH || name.length() > MAX_GIFT_CERTIFICATE_NAME_LENGTH) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH);
        }
        Pattern namePattern = Pattern.compile(TAG_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        if (!matcher.find()) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_NAME_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_NAME);
        }
    }

    private void validateDescription(String description) throws ValidatorException {
        if (description.length() > MAX_GIFT_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_LENGTH);
        }
    }

    private void validatePrice(Double price) throws ValidatorException {
        if (price == null || price < MIN_GIFT_CERTIFICATE_PRICE || price > MAX_GIFT_CERTIFICATE_PRICE) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_PRICE_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_PRICE);
        }
    }

    private void validateDuration(int duration) throws ValidatorException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new ValidatorException(INCORRECT_GIFT_CERTIFICATE_DURATION_EXCEPTION, INCORRECT_GIFT_CERTIFICATE_DURATION);
        }
    }

    public void validateMapKeys(MultiValueMap<String, String> mapWithParameters) throws ValidatorException {
        List<String> transferredKeys = new ArrayList<>(mapWithParameters.keySet());
        for (String transferredKey : transferredKeys) {
            if (!allowedKeys.contains(transferredKey)) {
                throw new ValidatorException(INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION, INCORRECT_TRANSFERRED_PARAMETERS);
            }
        }
    }
}




