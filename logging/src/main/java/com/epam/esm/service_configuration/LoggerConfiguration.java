package com.epam.esm.service_configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.epam.esm")
@EnableAspectJAutoProxy
public class LoggerConfiguration {
}
