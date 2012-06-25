package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.utils.StubOutput;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isA;

public class GameMakerTest {
    private final StubOutput output = new StubOutput();

    @Test public void
    adds_a_new_player() {
        GameMaker maker = new GameMaker(output.stream());
        maker.add("Bob");

        assertThat(output.contents(), contains("Bob was added", "They are player number 1"));
    }

    @Test public void
    adds_multiple_players() {
        GameMaker maker = new GameMaker(output.stream());
        maker.add("Julian");
        maker.add("Dick");
        maker.add("George");
        maker.add("Anne");
        maker.add("Timmy");

        assertThat(output.contents(), contains(
            "Julian was added", "They are player number 1",
            "Dick was added", "They are player number 2",
            "George was added", "They are player number 3",
            "Anne was added", "They are player number 4",
            "Timmy was added", "They are player number 5"
        ));
    }

    @Test public void
    creates_a_game() {
        GameMaker maker = new GameMaker(output.stream());
        maker.add("Frank");

        assertThat(maker.makeGame(), isA(Game.class));
    }
}
