package com.wordz.domain;

import java.util.ArrayList;
import java.util.List;

public class Score {

    private final String correct;
    private List<Letter> results = new ArrayList<>();
    private int position;

    public Score(String correct) {

        this.correct = correct;
    }

    public Letter letter(int position) {
        return results.get(position);
    }

    public void assess(String attempt) {

        for (char current : attempt.toCharArray()) {
            results.add(forScore(current));
            position++;
        }
    }

    private Letter forScore(char current) {
        if (isCorrectLetter(current)) {
            return Letter.CORRECT;
        }
        if (occursInWord(current)) {
            return Letter.PART_CORRECT;
        }
        return Letter.INCORRECT;
    }

    private boolean occursInWord(char current) {
        return correct.contains(String.valueOf(current));
    }

    private boolean isCorrectLetter(char currentLetter) {
        return correct.charAt(position) == currentLetter;
    }

    public boolean allCorrect() {
        long totalCorrect = results.stream()
                .filter(letter -> letter == Letter.CORRECT)
                .count();
        return totalCorrect == correct.length();
    }
}
