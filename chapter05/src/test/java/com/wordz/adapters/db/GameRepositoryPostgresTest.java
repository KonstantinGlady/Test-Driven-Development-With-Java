package com.wordz.adapters.db;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.wordz.domain.Game;
import com.wordz.domain.GameRepository;
import com.wordz.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

@DBRider
@DBUnit(caseSensitiveTableNames = true, caseInsensitiveStrategy = Orthography.LOWERCASE)
public class GameRepositoryPostgresTest {

    private DataSource dataSource;
    @SuppressWarnings("unused")
    private final ConnectionHolder connectionHolder = () -> dataSource.getConnection();

    @BeforeEach
    void setupConnection() {
        this.dataSource = new PostgresTestDataSource();
    }
    //todo 1: create table game (player_name character varying not null, word character(5), attempt_number integer default 0, is_game_over boolean default false);

    @Test
    @DataSet("adapters/data/emptyGame.json")
    @ExpectedDataSet("adapters/data/createGame.json")
    void storesNewGame() {
        Player player = new Player("player1");
        Game game = new Game(player, "BONUS", 0, false);
        GameRepository games = new GameRepositoryPostgres(dataSource);
        games.create(game);
    }
}
