package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;


/**
 * Class DevelopmentConfiguration is a configuration class which enables to connect to H2 database
 */
@Profile("dev")
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application-dev.properties")
@EnableJpaRepositories(basePackages = "com.epam.esm")
@EnableTransactionManagement
public class DevelopmentConfiguration {

    private final Environment environment;
    private static final String DB_DRIVER = "spring.datasource.driver-class-login";
    private static final String PACKAGES_TO_SCAN = "com.epam.esm";
    private static final String URL = "spring.datasource.url";
    private static final String PASSWORD = "spring.datasource.password";
    private static final String USER_NAME = "spring.datasource.username";
    private static final String HIBERNATE_DDL = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    @Autowired
    public DevelopmentConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Method entityManagerFactory helps to get LocalContainerEntityManagerFactoryBean - is the most powerful
     * JPA configuration option, enabling flexible local configuration in the application.
     * @return LocalContainerEntityManagerFactoryBean entity
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(PACKAGES_TO_SCAN);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(this.additionalProperties());
        return em;
    }

    /**
     * Method getDataSource is a factory for connections to the physical databases
     * @return DataSource entity
     */
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty(DB_DRIVER)));
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USER_NAME));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }

    /**
     * Method getTransactionManager helps to get the PlatformTransactionManager entity
     * @return PlatformTransactionManager entity
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    /**
     * Method exceptionTranslation helps to get PersistenceExceptionTranslationPostProcessor which translates
     * native resource exceptions to the Spring DataAccessException hierarchy
     * @return PersistenceExceptionTranslationPostProcessor entity
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Method additionalProperties helps to add additional properties to the JPA configuration
     * @return Properties properties
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_DDL,environment.getProperty(HIBERNATE_DDL));
        properties.setProperty(HIBERNATE_DIALECT,environment.getProperty(HIBERNATE_DIALECT));
        return properties;
    }
}
