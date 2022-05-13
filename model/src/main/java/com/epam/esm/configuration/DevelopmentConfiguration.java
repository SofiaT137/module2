package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


/**
 * Class DevelopmentConfiguration is a configuration class which enables to connect to EmbeddedDatabaseType.H2.
 */
@Profile("dev")
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application-dev.properties")
@EnableTransactionManagement
public class DevelopmentConfiguration {

    /**
     * Method getDataSource is a factory for connections to the physical databases
     * @return DataSource entity (The connection factory to EmbeddedDatabaseType.H2)
     */
    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    /**
     * Method getEntityManagerFactory helps to get the EntityManagerFactory entity
     * @return EntityManagerFactory entity
     */
    @Bean
    public EntityManagerFactory getEntityManagerFactory(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.epam.esm");
        factory.setDataSource(getDataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /**
     * Method getEntityManager helps to get the EntityManager entity
     * @param entityManagerFactory EntityManagerFactory entity
     * @return EntityManager entity
     */
    @Bean
    public EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Method getTransactionManager helps to get the TransactionManager entity
     * @return PlatformTransactionManager entity
     */
    @Bean
    public PlatformTransactionManager getTransactionManager(){
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(getEntityManagerFactory());
        return txManager;
    }
}
