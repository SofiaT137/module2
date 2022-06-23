package com.epam.esm.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectLogging.class);
    private static final String EXECUTION_ALL_SERVICE_METHODS = "execution(* com.epam.esm.service.*.*(..))";
    private static final String EXECUTION_ALL_MODEL_METHODS = "execution(* com.epam.esm.dao.*.*(..))";
    private static final String SERVICE_POINTCUT_REFERENCE_NAME = "allServiceMethods()";
    private static final String MODEL_POINTCUT_REFERENCE_NAME = "allModelMethods()";
    private static final String SERVICE_LAYER_METHOD_NAME = "Service layer, Method name: ";
    private static final String MODEL_LAYER_METHOD_NAME = "Model layer, Method name: ";
    private static final String SERVICE_LAYER_METHOD_THROWS_AN_EXCEPTION =
            "Method from Service layer throws an exception: ";
    private static final String MODEL_LAYER_METHOD_THROWS_AN_EXCEPTION =
            "Method from Model layer throws an exception: ";
    private static final String STARTS = " starts";
    private static final String EXCEPTION = "exception";

    /**
     * The allServiceMethods method is used only for aspect logging logic
     */
    @Pointcut(EXECUTION_ALL_SERVICE_METHODS)
    private void allServiceMethods() {}

    /**
     * The allModelMethods method is used only for aspect logging logic
     */
    @Pointcut(EXECUTION_ALL_MODEL_METHODS)
    private void allModelMethods() {}

    /**
     * Before service methods' advice method
     * @param joinPoint JoinPoint joinPoint
     */
    @Before(SERVICE_POINTCUT_REFERENCE_NAME)
    public void beforeServiceMethodsAdvice(JoinPoint joinPoint) {
        LOGGER.info("{} {} {}",SERVICE_LAYER_METHOD_NAME,joinPoint.getSignature().getName(),STARTS);
    }

    /**
     * After throwing an exception service methods' advice method
     * @param exception Throwable exception
     */
    @AfterThrowing(pointcut = SERVICE_POINTCUT_REFERENCE_NAME, throwing = EXCEPTION)
    public void afterThrowingServiceMethodsAdvice(Throwable exception) {
        LOGGER.error("{} {}", SERVICE_LAYER_METHOD_THROWS_AN_EXCEPTION, exception);
    }

    /**
     * Before model methods' advice method
     * @param joinPoint JoinPoint joinPoint
     */
    @Before(MODEL_POINTCUT_REFERENCE_NAME)
    public void beforeModelMethodsAdvice(JoinPoint joinPoint) {
        LOGGER.info("{} {} {}",MODEL_LAYER_METHOD_NAME,joinPoint.getSignature().getName(),STARTS);
    }

    /**
     * After throwing an exception model methods' advice method
     * @param exception Throwable exception
     */
    @AfterThrowing(pointcut = MODEL_POINTCUT_REFERENCE_NAME, throwing = EXCEPTION)
    public void afterThrowingModelMethodsAdvice(Throwable exception) {
        LOGGER.error("{} {}", MODEL_LAYER_METHOD_THROWS_AN_EXCEPTION, exception);
    }
}

