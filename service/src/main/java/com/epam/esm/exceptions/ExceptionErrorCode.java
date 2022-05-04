package com.epam.esm.exceptions;

/**
 * The ExceptionErrorCode final class contains all the ValidatorException custom codes
 */
public final class ExceptionErrorCode {

    private ExceptionErrorCode() {}

    public static final String NO_SUCH_ENTITY_CODE = "404001";
    public static final String CANNOT_INSERT_ENTITY_CODE = "404002";
    public static final String YOU_NOT_HAVE_PERMISSION_ENTITY_CODE = "403000";
    public static final String INCORRECT_ID = "403001";
    public static final String INCORRECT_TAG_NAME = "403002";
    public static final String INCORRECT_TAG_LENGTH = "403003";
    public static final String INCORRECT_GIFT_CERTIFICATE_NAME = "403004";
    public static final String INCORRECT_GIFT_CERTIFICATE_NAME_LENGTH = "403005";
    public static final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION_LENGTH = "403006";
    public static final String INCORRECT_GIFT_CERTIFICATE_PRICE = "403007";
    public static final String INCORRECT_GIFT_CERTIFICATE_DURATION = "403008";
    public static final String INCORRECT_TRANSFERRED_PARAMETERS = "403009";

}
