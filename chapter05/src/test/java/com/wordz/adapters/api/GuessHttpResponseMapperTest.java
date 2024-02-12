package com.wordz.adapters.api;

import com.wordz.domain.GuessResult;
import com.wordz.domain.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GuessHttpResponseMapperTest {
    private GuessHttpResponse actual;

    @BeforeEach
    void setup() {
        var score = new Score("CBCCZ");
        score.assess("ZBXXX");
        var result = new GuessResult(score, false, false);
        actual = new GuessHttpResponseMapper().from(result);
    }

    @Test
    void mapsPartCorrect() {
        assertThat(actual.score().charAt(0)).isEqualTo('P');
    }

    @Test
    void mapsCorrect() {
        assertThat(actual.score().charAt(1)).isEqualTo('C');
    }

    @Test
    void mapsIncorrect() {
        assertThat(actual.score().charAt(3)).isEqualTo('X');
    }

    @Test
    void mapsGameOver() {
        assertThat(actual.isGameOver()).isFalse();
    }
}
