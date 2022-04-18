package com.epam.esm.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* com.epam.esm.service.*.*(..))")
    private void allServiceMethods() {
    }

    @Pointcut("execution(* com.epam.esm.jbdc.*.*(..))")
    private void allModelMethods() {
    }

    @Before("allServiceMethods()")
    public void beforeServiceMethodsAdvice(JoinPoint joinPoint) {
        LOGGER.info("Service layer, Method name: " + joinPoint.getSignature().getName() + " starts");
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "exception")
    public void afterThrowingServiceMethodsAdvice(Throwable exception) {
        LOGGER.error("Method from Service layer throws an exception: " + exception);
    }

    @Before("allModelMethods()")
    public void beforeModelMethodsAdvice(JoinPoint joinPoint) {
        LOGGER.info("Model layer, Method name: " + joinPoint.getSignature().getName() + " starts");
    }

    @AfterThrowing(pointcut = "allModelMethods()", throwing = "exception")
    public void afterThrowingModelMethodsAdvice(Throwable exception) {
        LOGGER.error("Method from Model layer throws an exception: " + exception);
    }
}
