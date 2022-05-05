package com.epam.esm.internalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translation {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public Translation(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String translate(String message){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }
}
