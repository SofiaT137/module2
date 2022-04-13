package com.epam.esm.jbdc.configuration;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration//источник определения бинов
@ComponentScan("com.epam.esm") // По умолчанию, такая конфигурация сканирует на наличие классов с аннотацией @Component и его потомков в том пакете, в котором сама находится, а также в подпакетах.
@PropertySource("classpath:dbConnection.properties")
public class MySQLDataBaseConfiguration{

    private final Environment environment;

    private static final String DATA_BASE_USER = "USER";
    private static final String DATA_BASE_PASSWORD = "PASSWORD";
    private static final String DATA_BASE_DRIVER = "DB_DRIVER";
    private static final String DATA_BASE_URL = "DB_URL";
    private static final Integer POOL_SIZE = 4;


    @Autowired
    public MySQLDataBaseConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * This method creates bean of a DataSource object is the preferred means of getting a connection.
     * (The BasicDataSource object produces a standard Connection object for connecting to the physical data source.)
     * @return BasicDataSource object
     */
    @Bean //используется для указания того, что метод создает, настраивает и инициализирует новый объект, управляемый Spring IoC контейнером.
    public DataSource dataSource(){
        BasicDataSource basicDS = new BasicDataSource();
        basicDS.setUsername(environment.getProperty(DATA_BASE_USER));
        basicDS.setPassword(environment.getProperty(DATA_BASE_PASSWORD));
        basicDS.setDriverClassName(environment.getProperty(DATA_BASE_DRIVER));
        basicDS.setUrl(environment.getProperty(DATA_BASE_URL));
        basicDS.setInitialSize(POOL_SIZE);
        return basicDS;
    }

    /**
     * This method creates JdbcTemplate entity
     * @param dataSource The data source
     * @return The JdbcTemplate object
     */
    @Bean
    public JdbcTemplate getJBDCTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource());
    }


}
