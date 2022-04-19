package com.epam.esm.jbdc.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * DevelopmentProfileConfig class presents the 'dev' @profile configuration class.
 * The class declares @Bean methods and may be processed by the Spring container to generate bean definitions.
 */
@Configuration
@ComponentScan("com.epam.esm")
@Profile("dev")
public class DevelopmentProfileConfig {

    private static final String CREATE_SCHEMA = "classpath:createTables.sql";
    private static final String TEST_DATA = "classpath:fillTables.sql";
    private static final String DESTROY_METHOD_NAME = "shutdown";

    /**
     * This method creates bean of a DataSource object is the preferred means of getting a connection.
     * (The BasicDataSource object produces a standard Connection object for connecting to the physical data source.)
     * @return BasicDataSource object
     */

    @Bean(destroyMethod=DESTROY_METHOD_NAME)
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(CREATE_SCHEMA)
                .addScript(TEST_DATA)
                .build();
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
