package com.wordz.domain;

import org.junit.jupiter.api.Test;

public class NewGameTest {

    @Test
    void startsNewGame() {

        var game = new Game();
        var player = new Player();
        game.start(player);
    }
}
