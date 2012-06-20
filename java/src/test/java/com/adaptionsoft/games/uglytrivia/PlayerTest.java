package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {
    @Test public void
    the_player_starts_at_position_zero() {
        Player player = new Player("Me");
        assertThat(player.place(), is(0));
    }

    @Test public void
    the_player_moves_his_piece() {
        Player player = new Player("Me");
        player.incrementPlaceBy(3);
        assertThat(player.place(), is(3));
        player.incrementPlaceBy(4);
        assertThat(player.place(), is(7));
    }

    @Test public void
    moving_past_position_11_wraps_round() {
        Player player = new Player("Me");
        player.incrementPlaceBy(3);
        assertThat(player.place(), is(3));
        player.incrementPlaceBy(4);
        assertThat(player.place(), is(7));
        player.incrementPlaceBy(4);
        assertThat(player.place(), is(11));
        player.incrementPlaceBy(1);
        assertThat(player.place(), is(0));
    }
}
