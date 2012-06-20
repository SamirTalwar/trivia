package com.adaptionsoft.games.uglytrivia;

public interface Answerer {
    public static enum Answer {
        Correct,
        Incorrect
    }

    Answer answer(Question question);
}
