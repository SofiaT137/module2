package com.epam.esm.jbdc.configuration;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:dbConnection.properties")
public class MySQLDataBaseConfiguration{

    @Value("${DB_URL}")
    private String url;
    @Value("${USER}")
    private String user;
    @Value("${PASSWORD}")
    private String password;
    @Value("${DB_DRIVER}")
    private String dbDriver;
    @Value("${INITIAL_SIZE}")
    private Integer poolSize;

    /**
     * This method creates bean of a DataSource object is the preferred means of getting a connection.
     * (The BasicDataSource object produces a standard Connection object for connecting to the physical data source.)
     * @return BasicDataSource object
     */
    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDS = new BasicDataSource();
        basicDS.setUsername(user);
        basicDS.setPassword(password);
        basicDS.setDriverClassName(dbDriver);
        basicDS.setUrl(url);
        basicDS.setInitialSize(poolSize);
        return basicDS;
    }

    /**
     * This method creates JdbcTemplate object witch can be used for
     * @param dataSource The data source
     * @return The JdbcTemplate object
     */
    @Bean
    public JdbcTemplate getJBDCTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource());
    }


}
