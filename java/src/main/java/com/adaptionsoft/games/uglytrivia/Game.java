package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

public class Game {
    public static final int PLACES = 12;

    private final List<Player> players = new ArrayList<>();

    private final Map<Category, LinkedList<Question>> questions = new HashMap<>();
    private final List<Category> categories = unmodifiableList(Arrays.asList(
            new Category("Pop"),
            new Category("Science"),
            new Category("Sports"),
            new Category("Rock")
    ));

    {
        for (Category category : categories) {
            LinkedList<Question> categoryQuestions = new LinkedList<>();
            for (int i = 0; i < 50; i++) {
                categoryQuestions.addLast(new Question(category + " Question " + i));
            }
            questions.put(category, categoryQuestions);
        }
    }

    private final PrintStream out;

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game(PrintStream out) {
        this.out = out;
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        out.println(playerName + " was added");
        out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayer);
        out.println(player.name + " is the current player");
        out.println("They have rolled a " + roll);

        if (player.inPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                out.println(player.name + " is getting out of the penalty box");

                movePlayerBy(roll, player);
                askQuestion(player);
            } else {
                out.println(player.name + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            movePlayerBy(roll, player);
            askQuestion(player);
        }
    }

    public boolean answerCorrectly() {
        Player player = players.get(currentPlayer);
        if (player.inPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                player.inPenaltyBox = false;
                showPlayerAnsweredTheQuestionCorrectly(player);
                nextPlayer();
                return didPlayerWin(player);
            } else {
                nextPlayer();
                return false;
            }
        } else {
            showPlayerAnsweredTheQuestionCorrectly(player);
            nextPlayer();
            return didPlayerWin(player);
        }
    }

    public boolean answerIncorrectly() {
        Player player = players.get(currentPlayer);
        if (!player.inPenaltyBox || isGettingOutOfPenaltyBox) {
            out.println("Question was incorrectly answered");
            out.println(player.name + " was sent to the penalty box");
            player.inPenaltyBox = true;
        }

        nextPlayer();
        return false;
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size())
            currentPlayer = 0;
    }

    private void movePlayerBy(int roll, Player player) {
        player.incrementPlaceBy(roll);
        out.println(player.name + "'s new location is " + player.place());
    }

    private void askQuestion(Player player) {
        Category category = currentCategory(player);
        out.println("The category is " + category);
        out.println(questions.get(category).removeFirst());
    }

    private Category currentCategory(Player player) {
        int place = player.place();
        return categories.get(place % categories.size());
    }

    private void showPlayerAnsweredTheQuestionCorrectly(Player player) {
        out.println("Answer was correct!!!!");
        player.purse++;
        out.println(player.name + " now has " + player.purse + " Gold Coins.");
    }

    private boolean didPlayerWin(Player player) {
        return player.purse == 6;
    }
}
