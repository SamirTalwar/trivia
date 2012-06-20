package com.adaptionsoft.games.uglytrivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GameTest {
    private static final Answerer answerCorrectly = new Answerer() {
        @Override public Answer answer(Question question) {
            return Answer.Correct;
        }
    };
    private static final Answerer answerIncorrectly = new Answerer() {
        @Override public Answer answer(Question question) {
            return Answer.Incorrect;
        }
    };

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    @Test public void
    adds_a_new_player() {
        Game game = new Game(out);
        game.add("Bob");
        assertThat(output(), contains("Bob was added", "They are player number 1"));
    }

    @Test public void
    adds_multiple_players() {
        Game game = new Game(out);
        game.add("Julian");
        game.add("Dick");
        game.add("George");
        game.add("Anne");
        game.add("Timmy");
        assertThat(output(), contains(
            "Julian was added", "They are player number 1",
            "Dick was added", "They are player number 2",
            "George was added", "They are player number 3",
            "Anne was added", "They are player number 4",
            "Timmy was added", "They are player number 5"
        ));
    }

    @Test public void
    players_are_asked_questions() {
        Game game = new Game(out);
        game.add("Fred");
        game.add("George");
        game.add("Ron");

        flushOutput();
        assertThat(game.move(1, answerCorrectly), is(false));
        assertThat(game.move(2, answerCorrectly), is(false));
        assertThat(game.move(3, answerCorrectly), is(false));

        assertThat(output(), contains(
            "Fred is the current player", "They have rolled a 1",
            "Fred's new location is 1", "The category is Science", "Science Question 0",
            "Answer was correct!!!!", "Fred now has 1 Gold Coins.",
            "George is the current player", "They have rolled a 2",
            "George's new location is 2", "The category is Sports", "Sports Question 0",
            "Answer was correct!!!!", "George now has 1 Gold Coins.",
            "Ron is the current player", "They have rolled a 3",
            "Ron's new location is 3", "The category is Rock", "Rock Question 0",
            "Answer was correct!!!!", "Ron now has 1 Gold Coins."
        ));
    }

    @Test public void
    players_are_sent_to_the_penalty_box_for_wrong_answers() {
        Game game = new Game(out);
        game.add("Calvin");
        game.add("Hobbes");

        flushOutput();
        assertThat(game.move(2, answerCorrectly), is(false));
        assertThat(game.move(1, answerCorrectly), is(false));
        assertThat(game.move(1, answerIncorrectly), is(false));

        assertThat(output(), contains(
            "Calvin is the current player", "They have rolled a 2",
            "Calvin's new location is 2", "The category is Sports", "Sports Question 0",
            "Answer was correct!!!!", "Calvin now has 1 Gold Coins.",
            "Hobbes is the current player", "They have rolled a 1",
            "Hobbes's new location is 1", "The category is Science", "Science Question 0",
            "Answer was correct!!!!", "Hobbes now has 1 Gold Coins.",
            "Calvin is the current player", "They have rolled a 1",
            "Calvin's new location is 3", "The category is Rock", "Rock Question 0",
            "Question was incorrectly answered", "Calvin was sent to the penalty box"
        ));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    players_attempt_to_leave_the_penalty_box_when_they_roll_an_odd_number() {
        for (int roll : collectionOf(1, 3)) {
            Game game = new Game(out);
            game.add("Calvin");
            game.add("Hobbes");

            flushOutput();
            game.move(4, answerIncorrectly);
            game.move(3, answerIncorrectly);
            assertThat(game.move(roll, answerCorrectly), is(false));

            assertThat(output(), contains(
                equalTo("Calvin is the current player"), equalTo("They have rolled a 4"),
                equalTo("Calvin's new location is 4"), equalTo("The category is Pop"), equalTo("Pop Question 0"),
                equalTo("Question was incorrectly answered"), equalTo("Calvin was sent to the penalty box"),
                equalTo("Hobbes is the current player"), equalTo("They have rolled a 3"),
                equalTo("Hobbes's new location is 3"), equalTo("The category is Rock"), equalTo("Rock Question 0"),
                equalTo("Question was incorrectly answered"), equalTo("Hobbes was sent to the penalty box"),
                equalTo("Calvin is the current player"), equalTo("They have rolled a " + roll),
                equalTo("Calvin is getting out of the penalty box"),
                equalTo("Calvin's new location is " + (roll + 4)), any(String.class), any(String.class),
                equalTo("Answer was correct!!!!"), equalTo("Calvin now has 1 Gold Coins.")
            ));
        }
    }

    @SuppressWarnings("unchecked")
    @Test public void
    players_go_back_into_the_penalty_box_when_they_leave_but_get_the_question_wrong() {
        for (int roll : collectionOf(1, 3)) {
            Game game = new Game(out);
            game.add("Calvin");
            game.add("Hobbes");

            flushOutput();
            game.move(4, answerIncorrectly);
            game.move(3, answerIncorrectly);
            assertThat(game.move(roll, answerIncorrectly), is(false));

            assertThat(output(), contains(
                equalTo("Calvin is the current player"), equalTo("They have rolled a 4"),
                equalTo("Calvin's new location is 4"), equalTo("The category is Pop"), equalTo("Pop Question 0"),
                equalTo("Question was incorrectly answered"), equalTo("Calvin was sent to the penalty box"),
                equalTo("Hobbes is the current player"), equalTo("They have rolled a 3"),
                equalTo("Hobbes's new location is 3"), equalTo("The category is Rock"), equalTo("Rock Question 0"),
                equalTo("Question was incorrectly answered"), equalTo("Hobbes was sent to the penalty box"),
                equalTo("Calvin is the current player"), equalTo("They have rolled a " + roll),
                equalTo("Calvin is getting out of the penalty box"),
                equalTo("Calvin's new location is " + (roll + 4)), any(String.class), any(String.class),
                equalTo("Question was incorrectly answered"), equalTo("Calvin was sent to the penalty box")
            ));
        }
    }

    @Test public void
    players_stay_in_the_penalty_box_when_they_roll_an_even_number() {
        for (int roll : collectionOf(0, 2)) {
            Game game = new Game(out);
            game.add("Calvin");
            game.add("Hobbes");

            flushOutput();
            game.move(4, answerIncorrectly);
            game.move(3, answerIncorrectly);
            assertThat(game.move(roll, answerCorrectly), is(false));

            assertThat(output(), contains(
                "Calvin is the current player", "They have rolled a 4",
                "Calvin's new location is 4", "The category is Pop", "Pop Question 0",
                "Question was incorrectly answered", "Calvin was sent to the penalty box",
                "Hobbes is the current player", "They have rolled a 3",
                "Hobbes's new location is 3", "The category is Rock", "Rock Question 0",
                "Question was incorrectly answered", "Hobbes was sent to the penalty box",
                "Calvin is the current player", "They have rolled a " + roll,
                "Calvin is not getting out of the penalty box"
            ));
        }
    }

    @Test public void
    the_game_ends_when_a_player_has_six_golden_coins() {
        Game game = new Game(out);
        game.add("Sherlock");
        game.add("John");

        game.move(2, answerCorrectly);      game.move(1, answerCorrectly);
        game.move(0, answerIncorrectly);    game.move(3, answerCorrectly);
        game.move(3, answerCorrectly);      game.move(1, answerIncorrectly);
        game.move(2, answerCorrectly);      game.move(2, answerIncorrectly);
        game.move(1, answerCorrectly);      game.move(3, answerCorrectly);
        game.move(1, answerCorrectly);      game.move(1, answerCorrectly);
        flushOutput();
        assertThat(game.move(0, answerCorrectly), is(true));

        System.err.println(output());
        assertThat(output(), contains(
            "Sherlock is the current player", "They have rolled a 0",
            "Sherlock's new location is 9", "The category is Science", "Science Question 5",
            "Answer was correct!!!!", "Sherlock now has 6 Gold Coins."
        ));
    }

    private Iterable<String> output() {
        return Arrays.asList(outputStream.toString().replaceFirst("\n$", "").split("\n"));
    }

    private void flushOutput() {
        outputStream.reset();
    }

    private static <T> Collection<T> collectionOf(@SuppressWarnings("unchecked") T... items) {
        return Arrays.asList(items);
    }
}
