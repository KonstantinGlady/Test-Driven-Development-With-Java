package com.wordz.domain;

public interface GameRepository {
    void create(Game game);

    Game fetchGameForPlayer(Player player);
}
