package com.example.SpringTestExample.configuration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class LiquibaseConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Liquibase liquibase(DataSource dataSource) {
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
            return new Liquibase("db/changelog/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException("Failed to initialize Liquibase", e);
        }

    }
}
