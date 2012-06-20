package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public class Player {
        private final String name;
        private int place = 0;
        private int purse = 0;
        private boolean inPenaltyBox = false;

        public Player(String name) {
            this.name = name;
        }
    }

    private final List<Player> players = new ArrayList<>();

    private final LinkedList<String> popQuestions = new LinkedList<>();
    private final LinkedList<String> scienceQuestions = new LinkedList<>();
    private final LinkedList<String> sportsQuestions = new LinkedList<>();
    private final LinkedList<String> rockQuestions = new LinkedList<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayer);
        System.out.println(player.name + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (player.inPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(player.name + " is getting out of the penalty box");
                player.place = player.place + roll;
                if (player.place > 11)
                    player.place = player.place - 12;

                System.out.println(player.name + "'s new location is " + player.place);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(player.name + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            player.place = player.place + roll;
            if (player.place > 11)
                player.place = player.place - 12;

            System.out.println(player.name + "'s new location is " + player.place);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }
    }

    public boolean wasCorrectlyAnswered() {
        Player player = players.get(currentPlayer);
        if (player.inPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                player.inPenaltyBox = false;
                System.out.println("Answer was correct!!!!");
                player.purse++;
                System.out.println(player.name + " now has " + player.purse + " Gold Coins.");

                boolean winner = didPlayerWin();
                nextPlayer();

                return winner;
            } else {
                nextPlayer();
                return true;
            }
        } else {
            System.out.println("Answer was correct!!!!");
            player.purse++;
            System.out.println(player.name + " now has " + player.purse + " Gold Coins.");

            boolean winner = didPlayerWin();
            nextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayer);
        System.out.println("Question was incorrectly answered");
        System.out.println(player.name + " was sent to the penalty box");
        player.inPenaltyBox = true;

        nextPlayer();
        return true;
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size())
            currentPlayer = 0;
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
    }

    private String currentCategory() {
        int place = players.get(currentPlayer).place;
        if (place == 0) return "Pop";
        if (place == 4) return "Pop";
        if (place == 8) return "Pop";
        if (place == 1) return "Science";
        if (place == 5) return "Science";
        if (place == 9) return "Science";
        if (place == 2) return "Sports";
        if (place == 6) return "Sports";
        if (place == 10) return "Sports";
        return "Rock";
    }

    private boolean didPlayerWin() {
        return !(players.get(currentPlayer).purse == 6);
    }
}
