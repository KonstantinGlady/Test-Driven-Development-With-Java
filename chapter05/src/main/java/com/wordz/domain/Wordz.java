package com.wordz.domain;

public class Wordz {
    private final GameRepository gameRepository;
    private final WordSelection wordSelection;


    public Wordz(GameRepository gr, WordRepository wr, RandomNumbers rn) {
        this.gameRepository = gr;
        this.wordSelection = new WordSelection(wr, rn);
    }

    public void newGame(Player player) {
        var word = wordSelection.chooseRandomWord();
        var game = new Game(player, word, 0, false);
        gameRepository.create(game);
    }

    public GuessResult assess(Player player, String guess) {
        var game = gameRepository.fetchForPlayer(player);
        var score = game.attempt(guess);
        if (score.allCorrect()) {
            return new GuessResult(score, true, false);
        }
        gameRepository.update(game);
        return new GuessResult(score, game.hasNoRemainingGuesses(), false);
    }
}
