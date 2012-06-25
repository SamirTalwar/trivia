package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;

public class Question {
    private final String question;

    public Question(String question) {
        this.question = question;
    }

    public void writeTo(PrintStream out) {
        out.println(question);
    }
}
