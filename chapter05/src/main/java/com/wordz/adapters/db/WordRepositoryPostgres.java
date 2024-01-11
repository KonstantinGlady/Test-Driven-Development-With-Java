package com.wordz.adapters.db;

import com.wordz.domain.WordRepository;

import javax.sql.DataSource;

public class WordRepositoryPostgres implements WordRepository {
    public WordRepositoryPostgres(DataSource dataSource) {

    }

    @Override
    public String fetchWordByNumber(int number) {
        return null;
    }

    @Override
    public int highestWordNumber() {
        return 0;
    }
}
