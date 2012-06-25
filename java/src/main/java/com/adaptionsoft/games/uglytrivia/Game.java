package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.Answerer.Answer;
import com.google.common.collect.ImmutableList;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Game {
    public static final int PLACES = 12;
    public static final int GOLD_COINS_NEEDED_TO_WIN = 6;

    private final List<Player> players;

    private final Map<Category, Queue<Question>> questions = new HashMap<>();
    private final List<Category> categories = ImmutableList.of(
            new Category("Pop"),
            new Category("Science"),
            new Category("Sports"),
            new Category("Rock")
    );

    {
        for (Category category : categories) {
            Queue<Question> categoryQuestions = new ArrayDeque<>();
            for (int i = 0; i < 50; i++) {
                categoryQuestions.add(new Question(category + " Question " + i));
            }
            questions.put(category, categoryQuestions);
        }
    }

    private final PrintStream out;

    private int currentPlayer = 0;

    public Game(PrintStream out, List<Player> players) {
        this.out = out;
        this.players = ImmutableList.copyOf(players);
    }

    public boolean move(int roll, Answerer answerer) {
        Player player = players.get(currentPlayer);
        out.println(player.name() + " is the current player");
        out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 == 0) {
                out.println(player.name() + " is not getting out of the penalty box");
                nextPlayer();
                return false;
            }

            out.println(player.name() + " is getting out of the penalty box");
            player.moveOutOfPenaltyBox();
        }

        movePlayerBy(roll, player);
        return askQuestion(player, answerer);
    }

    private boolean askQuestion(Player player, Answerer answerer) {
        Category category = currentCategory(player);
        Question question = questions.get(category).poll();
        out.println("The category is " + category);
        out.println(question);
        Answer answer = answerer.answer(question);
        if (answer == Answer.Correct) {
            return answerCorrectly(player);
        } else {
            return answerIncorrectly(player);
        }
    }

    private boolean answerCorrectly(Player player) {
        player.grantAGoldCoin();
        out.println("Answer was correct!!!!");
        out.println(player.name() + " now has " + player.purse() + " Gold Coins.");
        nextPlayer();
        return player.hasWon();
    }

    private boolean answerIncorrectly(Player player) {
        player.moveIntoPenaltyBox();
        out.println("Question was incorrectly answered");
        out.println(player.name() + " was sent to the penalty box");
        nextPlayer();
        return false;
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    private void movePlayerBy(int roll, Player player) {
        player.incrementPlaceBy(roll);
        out.println(player.name() + "'s new location is " + player.place());
    }

    private Category currentCategory(Player player) {
        int place = player.place();
        return categories.get(place % categories.size());
    }
}
