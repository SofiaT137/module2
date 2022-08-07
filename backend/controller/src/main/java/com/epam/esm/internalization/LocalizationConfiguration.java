package com.epam.esm.internalization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The LocalizationConfiguration is a configuration class that helps setup
 * LocaleResolver and ResourceBundleMessageSource
 */
@Configuration
@EnableWebMvc
public class LocalizationConfiguration extends AcceptHeaderLocaleResolver {

    private static final String EN = "en";
    private static final String RU = "ru";
    private static final String MESSAGES_BASENAME = "messages";
    private static final String UTF = "UTF-8";

    private static final List<Locale> LOCALES = Arrays.asList(new Locale(EN),new Locale(RU));

    /**
     * Method resolveLocale helps to resolve locales by inspecting the accept-language header of an HTTP request.
     * @return LocaleResolver entity
     */
    @Bean
    public LocaleResolver resolveLocale(){
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(LOCALES);
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
}

    /**
     * Method ResourceBundleMessageSource accesses resource bundles using specified basenames.
     * @return ResourceBundleMessageSource entity
     */
    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename(MESSAGES_BASENAME);
        resourceBundleMessageSource.setDefaultEncoding(UTF);
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;
    }

}
