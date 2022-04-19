package com.epam.esm.service_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class ServiceConfiguration {

    private final DataSource dataSource;

    @Autowired
    public ServiceConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    //системный менеджер в JBDC для работы в спринге
    public DataSourceTransactionManager getDataSourceTransactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }
}
