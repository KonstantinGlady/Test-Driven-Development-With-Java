package com.wordz.domain;

public class Wordz {
    private final GameRepository gameRepository;

    public Wordz(GameRepository gr) {
        this.gameRepository = gr;
    }

    public void newGame(Player player) {

        var game = new Game(player,"ARISE",0);
        gameRepository.create(game);
    }
}
