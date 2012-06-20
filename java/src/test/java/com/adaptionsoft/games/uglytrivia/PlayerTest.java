package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {
    private final Player player = new Player("Me");

    @Test public void
    the_player_starts_at_position_zero() {
        assertThat(player.place(), is(0));
    }

    @Test public void
    the_player_moves_his_piece() {
        player.incrementPlaceBy(3);
        assertThat(player.place(), is(3));
        player.incrementPlaceBy(4);
        assertThat(player.place(), is(7));
    }

    @Test public void
    moving_past_position_11_wraps_round() {
        player.incrementPlaceBy(3);
        assertThat(player.place(), is(3));
        player.incrementPlaceBy(4);
        assertThat(player.place(), is(7));
        player.incrementPlaceBy(4);
        assertThat(player.place(), is(11));
        player.incrementPlaceBy(1);
        assertThat(player.place(), is(0));
    }

    @Test public void
    starts_outside_of_the_penalty_box() {
        assertThat(player.isInPenaltyBox(), is(false));
    }

    @Test public void
    can_be_moved_into_the_penalty_box() {
        player.moveIntoPenaltyBox();
        assertThat(player.isInPenaltyBox(), is(true));
    }

    @Test public void
    can_be_moved_out_of_the_penalty_box() {
        player.moveOutOfPenaltyBox();
        assertThat(player.isInPenaltyBox(), is(false));
    }

    @Test public void
    starts_with_no_coins() {
        assertThat(player.purse(), is(0));
    }

    @Test public void
    increments_the_purse_when_a_coin_is_granted() {
        player.grantAGoldCoin();
        assertThat(player.purse(), is(1));
        player.grantAGoldCoin();
        assertThat(player.purse(), is(2));
        player.grantAGoldCoin();
        assertThat(player.purse(), is(3));
    }

    @Test public void
    the_player_wins_when_he_has_six_gold_coins() {
        player.grantAGoldCoin();
        assertThat(player.hasWon(), is(false));
        player.grantAGoldCoin();
        assertThat(player.hasWon(), is(false));
        player.grantAGoldCoin();
        assertThat(player.hasWon(), is(false));
        player.grantAGoldCoin();
        assertThat(player.hasWon(), is(false));
        player.grantAGoldCoin();
        assertThat(player.hasWon(), is(false));
        player.grantAGoldCoin();
        assertThat(player.hasWon(), is(true));
    }
}
