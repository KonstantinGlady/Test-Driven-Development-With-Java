package com.wordz.domain;

public class Game {
    private final Player player;
    private final String targetWord;
    private final int attemptNumber;

    public Game(Player player, String targetWord, int attemptNumber) {

        this.player = player;
        this.targetWord = targetWord;
        this.attemptNumber = attemptNumber;
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
