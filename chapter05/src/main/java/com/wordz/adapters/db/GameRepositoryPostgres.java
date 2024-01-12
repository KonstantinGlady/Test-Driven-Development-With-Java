package com.wordz.adapters.db;

import com.wordz.domain.Game;
import com.wordz.domain.GameRepository;
import com.wordz.domain.Player;

import javax.sql.DataSource;

public class GameRepositoryPostgres implements GameRepository {
    private final DataSource dataSource;

    public GameRepositoryPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Game game) {

    }

    @Override
    public Game fetchForPlayer(Player player) {
        return null;
    }

    @Override
    public void update(Game game) {

    }
}
