package com.epam.esm.exceptions;

/**
 * The ExceptionErrorCode final class contains all the DaoException custom codes
 */
public final class ExceptionErrorCode {

    private ExceptionErrorCode() {}

    public static final String DATASOURCE_SAVING_ERROR = "404000";
    public static final String DATASOURCE_NOT_FOUND = "404001";
    public static final String DATASOURCE_NOT_FOUND_BY_NAME = "404002";
    public static final String DATASOURCE_NOT_FOUND_BY_ID = "404003";
    public static final String CANNOT_FIND_GIFT_CERTIFICATE_TAGS = "404004";
    public static final String CANNOT_ADD_NEW_TAG = "404005";
}
