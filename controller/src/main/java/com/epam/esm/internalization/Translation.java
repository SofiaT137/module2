package com.epam.esm.internalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class Translation helps to translate messages into the proper language
 */
@Component
public class Translation {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public Translation(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Method translate helps to translate transferred message depends on current locale
     * @param message String message
     * @return The translated message
     */
    public String translate(String message){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }
}
