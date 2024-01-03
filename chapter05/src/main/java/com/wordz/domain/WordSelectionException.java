package com.wordz.domain;

public class WordSelectionException extends RuntimeException {
    WordSelectionException(String reason, Throwable t) {
        super(reason, t);
    }
}
