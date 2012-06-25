package com.adaptionsoft.games.uglytrivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class AnswerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    @Test public void
    a_correct_answer_rewards_the_player() {
        Player player = new Player("Julie");

        assertThat(Answer.Correct.dealWith(player, out), is(false));

        assertThat(player.purse(), is(1));
        assertThat(output(), contains(
                "Answer was correct!!!!",
                "Julie now has 1 Gold Coins."
        ));
    }

    @Test public void
    a_correct_answer_returns_true_when_the_player_has_won() {
        Player player = new Player("Beatrice");
        for (int i = 0; i < 5; i++) {
            player.grantAGoldCoin();
        }

        assertThat(Answer.Correct.dealWith(player, out), is(true));

        assertThat(player.purse(), is(6));
        assertThat(output(), contains(
                "Answer was correct!!!!",
                "Beatrice now has 6 Gold Coins."
        ));
    }

    @Test public void
    an_incorrect_answer_sends_the_player_to_the_penalty_box() {
        Player player = new Player("Sophie");

        assertThat(Answer.Incorrect.dealWith(player, out), is(false));

        assertThat(player.purse(), is(0));
        assertThat(player.isInPenaltyBox(), is(true));
        assertThat(output(), contains(
                "Question was incorrectly answered",
                "Sophie was sent to the penalty box"
        ));
    }

    private Iterable<String> output() {
        return Arrays.asList(outputStream.toString().replaceFirst("\n$", "").split("\n"));
    }
}
