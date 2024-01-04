package com.wordz.domain;

import org.junit.jupiter.api.Test;

public class NewGameTest {

    @Test
    void startsNewGame() {

        var wordz = new Wordz();
        var player = new Player();
        wordz.newGame(player);
    }
}
