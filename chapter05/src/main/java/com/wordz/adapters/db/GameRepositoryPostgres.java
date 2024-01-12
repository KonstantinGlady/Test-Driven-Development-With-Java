package com.wordz.adapters.db;

import com.wordz.domain.Game;
import com.wordz.domain.GameRepository;
import com.wordz.domain.Player;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public class GameRepositoryPostgres implements GameRepository {
    private static final String SQL_INSERT_NEW_GAME_ROW =
            "insert into game (player_name, word, attempt_number, is_game_over)" +
                    "values(:playerName, :word, :attemptNumber, :isGameOver)";
    private final Jdbi jdbi;

    public GameRepositoryPostgres(DataSource dataSource) {
        jdbi = Jdbi.create(dataSource);
    }

    @Override
    public void create(Game game) {
        jdbi.useHandle(handle ->
                handle.createUpdate(SQL_INSERT_NEW_GAME_ROW)
                        .bind("playerName", game.getPlayer().getName())
                        .bind("word", game.getWord())
                        .bind("attemptNumber", game.getAttemptNumber())
                        .bind("isGameOver", game.isGameOver())
                        .execute()
        );
    }

    @Override
    public Game fetchForPlayer(Player player) {
        return null;
    }

    @Override
    public void update(Game game) {

    }
}
