package com.epam.esm.jbdc.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@Profile("dev")
public class DevelopmentProfileConfig {

    private static final String CREATE_SCHEMA = "classpath:createTables.sql";
    private static final String TEST_DATA = "classpath:fillTables.sql";

    @Bean(destroyMethod="shutdown")
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
