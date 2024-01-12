package com.wordz.adapters.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class PostgresTestDataSource extends PGSimpleDataSource {

    public PostgresTestDataSource() {

        setServerNames(new String[]{"localhost"});
        setDatabaseName("wordzdb");
        setCurrentSchema("public");
        setUser("ciuser");
        setPassword("cipassword");
    }
}

