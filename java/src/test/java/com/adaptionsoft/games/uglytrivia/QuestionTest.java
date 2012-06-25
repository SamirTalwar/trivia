package com.adaptionsoft.games.uglytrivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class QuestionTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    @Test public void
    writes_itself() {
        Question question = new Question("How much wood could a wood chuck chuck if a wood chuck could chuck wood?");
        question.writeTo(out);

        assertThat(output(), contains("How much wood could a wood chuck chuck if a wood chuck could chuck wood?"));
    }

    private Iterable<String> output() {
        return Arrays.asList(outputStream.toString().replaceFirst("\n$", "").split("\n"));
    }
}
