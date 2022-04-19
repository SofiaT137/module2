package com.epam.esm.logging_configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * LoggerConfiguration class presents aspect configuration class.
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableAspectJAutoProxy
public class LoggerConfiguration {
}
