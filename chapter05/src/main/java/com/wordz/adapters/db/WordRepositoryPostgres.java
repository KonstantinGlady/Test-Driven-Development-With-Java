package com.wordz.adapters.db;

import com.wordz.domain.WordRepository;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public class WordRepositoryPostgres implements WordRepository {

    private static final String SQL_FETCH_WORD_BY_NUMBER =
            "select word from word where word_number=:wordNumber";
    private final Jdbi jdbi;

    public WordRepositoryPostgres(DataSource dataSource) {
        jdbi = Jdbi.create(dataSource);
    }

    @Override
    public String fetchWordByNumber(int number) {

        var word = jdbi.withHandle((handle) -> {
            var query = handle.createQuery(SQL_FETCH_WORD_BY_NUMBER);
            query.bind("wordNumber", number);

            return query.mapTo(String.class).one();
        });

        return word;
    }

    @Override
    public int highestWordNumber() {
        return 0;
    }
}
