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
        var game = new Game(player, word, 0);
        gameRepository.create(game);
    }

    public GuessResult assess(Player player, String guess) {
        var game = gameRepository.fetchForPlayer(player);
        game.incrementAttemptNumber();
        gameRepository.update(game);
        var target = new Word(game.getWord());
        var score = target.guess(guess);
        return new GuessResult(score, false);
    }
}
