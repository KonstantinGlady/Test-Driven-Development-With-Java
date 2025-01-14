package com.wordz.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewGameTest {

    private final Player PLAYER = new Player("player1");
    private final String CORRECT_WORD = "ARISE";
    @Mock
    private GameRepository gameRepository;
    @Mock
    private WordRepository wordRepository;
    @Mock
    private RandomNumbers randomNumbers;
    @InjectMocks
    private Wordz wordz;

    @Test
    void startsNewGame() {

        givenWordToSelect(CORRECT_WORD);
        wordz.newGame(PLAYER);
        var game = getGameInRepository();

        assertThat(game.getWord()).isEqualTo(CORRECT_WORD);
        assertThat(game.getAttemptNumber()).isZero();
        assertThat(game.getPlayer()).isSameAs(PLAYER);
    }

    @Test
    void cannotRestartNewGame() {

        when(gameRepository.fetchForPlayer(PLAYER))
                .thenReturn(Optional.of(Game.create(PLAYER, CORRECT_WORD)));
        var success = wordz.newGame(PLAYER);
        assertThat(success).isFalse();
    }


    private Game getGameInRepository() {
        var gameArgument = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).create(gameArgument.capture());
        return gameArgument.getValue();
    }


    private void givenWordToSelect(String wordToSelect) {
        int wordNumber = 2;
        when(randomNumbers.next(anyInt())).thenReturn(wordNumber);
        when(wordRepository.fetchWordByNumber(wordNumber)).thenReturn(wordToSelect);
    }
}
