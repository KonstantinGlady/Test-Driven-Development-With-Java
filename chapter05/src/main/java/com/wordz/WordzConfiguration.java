package com.wordz;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class WordzConfiguration {
    private static final int NUMBER_OF_COMMAND_LINE_OPTIONS = 7;
    private String endpointHost = "localhost";
    private int endpointPort = 8080;
    private PGSimpleDataSource dataSource;
    private String databaseName = "wordzdb";
    private String databaseHost = "localhost";
    private String databaseSchemaName = "public";
    private String databaseUser = "ciuser";
    private String databaseUserPassword = "cipassword";

    public WordzConfiguration(String[] args) {
        extractValuesFromCommandLine(args);
        dataSource = createDataSource();
    }

    private PGSimpleDataSource createDataSource() {
        var ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{databaseHost});
        ds.setDatabaseName(databaseName);
        ds.setCurrentSchema(databaseSchemaName);
        ds.setUser(databaseUser);
        ds.setPassword(databaseUserPassword);
        return ds;
    }

    private void extractValuesFromCommandLine(String[] args) {
        if (!(args.length == NUMBER_OF_COMMAND_LINE_OPTIONS)) {
            return;
        }

        endpointHost = args[0];
        endpointPort = Integer.parseInt(args[1]);
        databaseName = args[2];
        databaseHost = args[3];
        databaseSchemaName = args[4];
        databaseUser = args[5];
        databaseUserPassword = args[6];
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public String getEndpointHost() {
        return endpointHost;
    }

    public int getEndpointPort() {
        return endpointPort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseSchemaName() {
        return databaseSchemaName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabaseUserPassword() {
        return databaseUserPassword;
    }
}
