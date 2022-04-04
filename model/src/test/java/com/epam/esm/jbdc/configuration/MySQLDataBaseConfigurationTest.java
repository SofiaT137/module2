package com.epam.esm.jbdc.configuration;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class MySQLDataBaseConfigurationTest {

    private static final String DB_URL = "DB_URL";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";
    private static final String CHARACTER_ENCODING = "CHARACTER_ENCODING";
    private static final String DATABASE_PROPERTIES = "src/main/resources/dbConnection.properties";

    @Test
    void getMySQLDataSource() throws SQLException, IOException {

        //Properties properties = new Properties();
        //FileReader reader = new FileReader(DATABASE_PROPERTIES);
        //properties.load(reader);
        //MysqlDataSource mysqlDS = new MysqlDataSource();

        //mysqlDS.setURL(properties.getProperty(DB_URL));
        //mysqlDS.setUser(properties.getProperty(USER));
        //mysqlDS.setPassword(properties.getProperty(PASSWORD));
        //mysqlDS.setCharacterEncoding(properties.getProperty(CHARACTER_ENCODING));

        //DataSourceInitializer initializer = new DataSourceInitializer();
        //initializer.setDataSource(mysqlDS);
        //ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        //databasePopulator.addScript(
         //       new ClassPathResource("model/src/main/resources/SQL/createTables.sql"));
        //initializer.setDatabasePopulator(databasePopulator);
        //Path path = Paths.get("src/main/resources/SQL/createTables.sql");
        //assertTrue(Files.exists(path));

    }

    }
