package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.utils.StubOutput;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class QuestionTest {
    private final StubOutput output = new StubOutput();

    @Test public void
    writes_itself() {
        Question question = new Question("How much wood could a wood chuck chuck if a wood chuck could chuck wood?");
        question.writeTo(output.stream());

        assertThat(output.contents(), contains("How much wood could a wood chuck chuck if a wood chuck could chuck wood?"));
    }
}
