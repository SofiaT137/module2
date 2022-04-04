package com.epam.esm.jbdc.configuration;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@PropertySource("classpath:dbConnection.properties")
public class MySQLDataBaseConfiguration{

    /**
     * This method creates bean of a DataSource object is the preferred means of getting a connection.
     * (The BasicDataSource object produces a standard Connection object for connecting to the physical data source.)
     * @param url The database url
     * @param user The database user
     * @param password The database password
     * @param dbDriver The database driver
     * @param poolSize The database connection pool size
     * @return BasicDataSource object
     */
    @Bean
    public DataSource dataSource(@Value("${DB_URL}") String url,
                                         @Value("${USER}") String user,
                                         @Value("${PASSWORD}") String password,
                                         @Value("${DB_DRIVER}") String dbDriver,
                                         @Value("${INITIAL_SIZE}") Integer poolSize){
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
        return new JdbcTemplate(dataSource);
    }


}
