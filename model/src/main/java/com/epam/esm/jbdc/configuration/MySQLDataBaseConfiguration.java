package com.epam.esm.jbdc.configuration;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.Objects;

/**
 * DevelopmentProfileConfig class presents the 'prod' @profile configuration class.
 * The class declares @Bean methods and may be processed by the Spring container to generate bean definitions.
 */
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:dbConnection.properties")
@Profile("prod")
public class MySQLDataBaseConfiguration{

    private final Environment environment;
    private PlatformTransactionManager transactionManager;

    private static final String DATA_BASE_USER = "USER";
    private static final String DATA_BASE_PASSWORD = "PASSWORD";
    private static final String DATA_BASE_DRIVER = "DB_DRIVER";
    private static final String DATA_BASE_URL = "DB_URL";
    private static final String POOL_SIZE = "INITIAL_SIZE";

    @Autowired
    public MySQLDataBaseConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * This method creates bean of a DataSource object is the preferred means of getting a connection.
     * (The BasicDataSource object produces a standard Connection object for connecting to the physical data source.)
     * @return BasicDataSource object
     */
    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDS = new BasicDataSource();
        basicDS.setUsername(environment.getProperty(DATA_BASE_USER));
        basicDS.setPassword(environment.getProperty(DATA_BASE_PASSWORD));
        basicDS.setDriverClassName(environment.getProperty(DATA_BASE_DRIVER));
        basicDS.setUrl(environment.getProperty(DATA_BASE_URL));
        basicDS.setInitialSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(POOL_SIZE))));
        return basicDS;
    }

    /**
     * This method creates JdbcTemplate entity
     * @param dataSource The data source
     * @return The JdbcTemplate entity
     */
    @Bean
    public JdbcTemplate getJBDCTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
