package com.wordz.adapters.db;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;


@DBRider
@DBUnit(caseSensitiveTableNames = true, caseInsensitiveStrategy = Orthography.LOWERCASE)
public class WordRepositoryPostgresTest {

    private DataSource dataSource;
    private ConnectionHolder connectionHolder = () -> dataSource.getConnection();

    @BeforeEach
    void setupConnection() {
        var ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setDatabaseName("wordzdb");
        ds.setCurrentSchema("public");
        ds.setUser("ciuser");
        ds.setPassword("ciPassword");
        this.dataSource = ds;
    }

    @Test
    void fetchWord() {

    }
}
