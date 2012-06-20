package com.adaptionsoft.games.uglytrivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);
    private final PrintStream originalOut = System.out;

    @Before public void
    stub_STDOUT() {
        System.setOut(out);
    }

    @After public void
    reset_STDOUT() {
        System.setOut(originalOut);
    }

    @Test public void
    adds_a_new_player() {
        Game game = new Game();
        game.add("Bob");
        assertThat(outputStream.toString(), is("Bob was added\nThey are player number 1\n"));
    }
}
