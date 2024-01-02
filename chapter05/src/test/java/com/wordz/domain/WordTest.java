package com.wordz.domain;

import org.junit.jupiter.api.Test;

import static com.wordz.domain.Letter.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WordTest {

    @Test
    void oneIncorrectLetter() {

        var word = new Word("A");
        var score = word.guess("Z");
        assertScoreForGuess(score, INCORRECT);
    }

    private void assertScoreForGuess(Score score, Letter... expected) {
        for (int position = 0; position < expected.length; position++) {
            assertThat(score.letter(position)).isEqualTo(expected[position]);
        }
    }

    @Test
    void oneCorrectLetter() {

        var word = new Word("A");
        var score = word.guess("A");
        assertScoreForGuess(score, CORRECT);
    }

    @Test
    void secondLetterWrongPosition() {

        var word = new Word("AR");
        var score = word.guess("ZA");
        assertScoreForGuess(score, INCORRECT, PART_CORRECT);
    }

    @Test
    void allScoreCombinations() {

        var word = new Word("ARI");
        var score = word.guess("ZAI");
        assertScoreForGuess(score, INCORRECT,
                PART_CORRECT,
                CORRECT);
    }
}
