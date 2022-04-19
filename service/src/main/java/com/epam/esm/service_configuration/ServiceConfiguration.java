package com.epam.esm.service_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * ServiceConfiguration class presents the configuration class.
 * The class declares @Bean methods and may be processed by the Spring container to generate bean definitions.
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class ServiceConfiguration {

    private final DataSource dataSource;

    @Autowired
    public ServiceConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * The DataSourceTransactionManager method creates @bean getDataSourceTransactionManager
     * TransactionManagers handle all of your transactional activities - running a query, wrapped in a transaction.
     * @return DataSourceTransactionManager entity
     */
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }
}
