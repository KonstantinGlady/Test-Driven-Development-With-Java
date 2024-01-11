package com.wordz.adapters.db;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.wordz.domain.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;


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
        ds.setPassword("cipassword");
        this.dataSource = ds;
    }

    //todo psql 1(user not exist): create user ciuser with password 'cipassword';
    //todo 2(database "wordzdb" does not exist): create database wordzdb;
    @Test
    @DataSet("adapters/data/wordTable.json")
    void fetchWord() {
        WordRepository wordRepository = new WordRepositoryPostgres(dataSource);
        String actual = wordRepository.fetchWordByNumber(27);
        assertThat(actual).isEqualTo("ARISE");
    }
    //todo 3(table word does not exist) create table word (word_number int primary key, word char(5));
    //todo 4 (permission denied for table word) grant select, insert, update, delete on all tables in schema public to ciuser;
}
