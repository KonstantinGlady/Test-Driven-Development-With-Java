package com.wordz.domain;

public class Game {
    private final Player player;
    private final String targetWord;
    private int attemptNumber;

    public Game(Player player, String targetWord, int attemptNumber) {

        this.player = player;
        this.targetWord = targetWord;
        this.attemptNumber = attemptNumber;
    }

    public static Game create(Player player, String correctWord) {
        return new Game(player, correctWord, 0);
    }

    public String getWord() {
        return targetWord;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public Player getPlayer() {
        return player;
    }
}
