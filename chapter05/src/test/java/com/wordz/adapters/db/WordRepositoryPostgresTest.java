package com.wordz.adapters.db;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;


@DBRider
@DBUnit(caseSensitiveTableNames = true, caseInsensitiveStrategy = Orthography.LOWERCASE)
public class WordRepositoryPostgresTest {

    private DataSource dataSource;

    private ConnectionHolder connectionHolder = () -> dataSource.getConnection();

    @Test
    void fetchWord() {

    }
}
