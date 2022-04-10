package com.epam.esm;

import com.epam.esm.configuration.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Initializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Creates context object (абстрактный класс, позволяет нам зарегистрировать один диспетчерский сервлет и создать корневой контекст веб-приложения)
        AnnotationConfigWebApplicationContext annotationConfigApplicationContext = new AnnotationConfigWebApplicationContext();
        // Registers annotated configurations class
        annotationConfigApplicationContext.register(WebConfig.class);//инициализируем контекст, т.е говорим, что этот класс источник определения бинов
        // Passes servlet context to context instance
        annotationConfigApplicationContext.setServletContext(servletContext);
        //Registers dispatch servlet and passes context instance
        DispatcherServlet servlet = new DispatcherServlet(annotationConfigApplicationContext);
        ServletRegistration.Dynamic servDyn = servletContext.addServlet("test",servlet);
        //Sets creation priority
        servDyn.setLoadOnStartup(1);
        //Maps URL pattern
        servDyn.addMapping("/");
        // Метод в начале создает абстрактный класс, который конфигурирует веб апликейтион контекст,
        // Апликейшен контекст спринг MVC. Он передает туда web application context с нашим апликейшен контекстом, и добавляет
        // Контекст нашего сервлета в общий веб контекст,Затем он создает новый диспатчер сервлет, передавая туда глобальные настройки
        // нашего веб приложения,затем он регестрирует новый сервлет, и говорит, что все URL паретны начинающиеся со слеша, обрабатываются нашим сервлетом

    }
}
