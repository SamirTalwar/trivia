package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.Answerer.Answer;
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
    public static final int GOLD_COINS_NEEDED_TO_WIN = 6;

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

    public boolean move(int roll, Answerer answerer) {
        Player player = players.get(currentPlayer);
        out.println(player.name + " is the current player");
        out.println("They have rolled a " + roll);

        if (player.inPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                out.println(player.name + " is getting out of the penalty box");

                movePlayerBy(roll, player);
                return askQuestion(player, answerer);
            } else {
                isGettingOutOfPenaltyBox = false;
                out.println(player.name + " is not getting out of the penalty box");
                nextPlayer();
                return false;
            }
        } else {
            movePlayerBy(roll, player);
            return askQuestion(player, answerer);
        }
    }

    private boolean askQuestion(Player player, Answerer answerer) {
        Category category = currentCategory(player);
        out.println("The category is " + category);
        Question question = questions.get(category).removeFirst();
        out.println(question);
        Answer answer = answerer.answer(question);
        if (answer == Answer.Correct) {
            return answerCorrectly(player);
        } else {
            return answerIncorrectly(player);
        }
    }

    private boolean answerCorrectly(Player player) {
        if (player.inPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                player.inPenaltyBox = false;
                nextPlayer();
                showPlayerAnsweredTheQuestionCorrectly(player);
                return player.hasWon();
            } else {
                nextPlayer();
                return false;
            }
        } else {
            nextPlayer();
            showPlayerAnsweredTheQuestionCorrectly(player);
            return player.hasWon();
        }
    }

    private boolean answerIncorrectly(Player player) {
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

    private Category currentCategory(Player player) {
        int place = player.place();
        return categories.get(place % categories.size());
    }

    private void showPlayerAnsweredTheQuestionCorrectly(Player player) {
        out.println("Answer was correct!!!!");
        player.grantAGoldCoin();
        out.println(player.name + " now has " + player.purse() + " Gold Coins.");
    }
}
