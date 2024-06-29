package com.wordz.domain;

import java.util.Optional;

public class Wordz {
    private final GameRepository gameRepository;
    private final WordSelection wordSelection;


    public Wordz(GameRepository gr, WordRepository wr, RandomNumbers rn) {
        this.gameRepository = gr;
        this.wordSelection = new WordSelection(wr, rn);
    }

    public boolean newGame(Player player) {
        var currentGame = gameRepository.fetchForPlayer(player);
        if (isGameInProgress(currentGame)) {

            return false;
        }
        var word = wordSelection.chooseRandomWord();
        var game = new Game(player, word, 0, false);
        gameRepository.create(game);
        return true;
    }

    private static boolean isGameInProgress(Optional<Game> currentGame) {
        if (currentGame.isEmpty()) {
            return false;
        }
        return !currentGame.get().isGameOver();
    }

    public GuessResult assess(Player player, String guess) {
        var game = gameRepository.fetchForPlayer(player);

        if (!isGameInProgress(game)) {
            return GuessResult.ERROR;
        }
        return calculateScore(guess, game.get());
    }

    private GuessResult calculateScore(String guess, Game game) {
        var score = game.attempt(guess);

        gameRepository.update(game);
        return GuessResult.create(score, game.isGameOver());
    }
}
