package com.wordz.domain;

public record GuessResult(Score score, boolean isGameOver, boolean isError) {
}
