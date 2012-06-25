package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.utils.StubOutput;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class AnswerTest {
    private final StubOutput output = new StubOutput();

    @Test public void
    a_correct_answer_rewards_the_player() {
        Player player = new Player("Julie");

        assertThat(Answer.Correct.dealWith(player, output.stream()), is(false));

        assertThat(player.purse(), is(1));
        assertThat(output.contents(), contains(
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

        assertThat(Answer.Correct.dealWith(player, output.stream()), is(true));

        assertThat(player.purse(), is(6));
        assertThat(output.contents(), contains(
                "Answer was correct!!!!",
                "Beatrice now has 6 Gold Coins."
        ));
    }

    @Test public void
    an_incorrect_answer_sends_the_player_to_the_penalty_box() {
        Player player = new Player("Sophie");

        assertThat(Answer.Incorrect.dealWith(player, output.stream()), is(false));

        assertThat(player.purse(), is(0));
        assertThat(player.isInPenaltyBox(), is(true));
        assertThat(output.contents(), contains(
                "Question was incorrectly answered",
                "Sophie was sent to the penalty box"
        ));
    }
}
