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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DataSet(value = "adapters/data/createGame.json", cleanBefore = true)
    void fetchesGame() {

        GameRepository games = new GameRepositoryPostgres(dataSource);
        Player player = new Player("player1");

        Optional<Game> game = games.fetchForPlayer(player);
        assertThat(game.isPresent()).isTrue();

        var actual = game.get();
        assertThat(actual.getPlayer()).isEqualTo(player);
        assertThat(actual.getWord()).isEqualTo("BONUS");
        assertThat(actual.getAttemptNumber()).isZero();
        assertThat(actual.isGameOver()).isFalse();
    }

    @Test
    @DataSet("adapters/data/emptyGame.json")
    void reportsGameNotFoundForPlayer() {
        GameRepository games = new GameRepositoryPostgres(dataSource);
        Player player = new Player("player1");
        Optional<Game> game = games.fetchForPlayer(player);
        assertThat(game.isEmpty()).isTrue();
    }

    @Test
    @DataSet(value = "adapters/data/createGame.json", cleanBefore = true)
    @ExpectedDataSet("adapters/data/updatedGame.json")
    void updatesGame() {
        Player player = new Player("player1");
        Game game = new Game(player, "BONUS", 0, false);
        GameRepository games = new GameRepositoryPostgres(dataSource);

        game.attempt("AAAAA");
        games.update(game);
    }
}
