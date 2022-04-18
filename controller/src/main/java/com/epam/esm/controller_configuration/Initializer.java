package com.epam.esm.controller_configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Class Initializer implements WebApplicationInitializer.
 * This class will be loaded when loading web project starts.
  */
public class Initializer implements WebApplicationInitializer {

    private static final String SERVLET_NAME = "test";
    private static final String SERVLET_MAPPING = "/";
    private static final String PROFILES_DEFAULT = "spring.profiles.default";
    private static final String PROD = "prod";
    private static final Integer LOAD_ON_START_UP = 1;

    /**
     * Method onStartup helps to create context object, register annotated configuration class, passes servlet context
     * to context instance, registers dispatch servlet and passes context instance to it, maps URL pattern, sets creation priority,
     * sets Init parameter
     * @param servletContext Servlet context
     * @throws ServletException Servlet exception
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext annotationConfigApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigApplicationContext.register(WebConfig.class);
        annotationConfigApplicationContext.setServletContext(servletContext);
        DispatcherServlet servlet = new DispatcherServlet(annotationConfigApplicationContext);
        servlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic servDyn = servletContext.addServlet(SERVLET_NAME,servlet);
        servDyn.setLoadOnStartup(LOAD_ON_START_UP);
        servDyn.addMapping(SERVLET_MAPPING);
        servDyn.setInitParameter(PROFILES_DEFAULT,PROD);
    }
}
