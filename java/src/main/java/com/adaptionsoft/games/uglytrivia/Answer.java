package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;

public enum Answer {
    Correct {
        @Override
        public boolean dealWith(Player player, PrintStream out) {
            player.grantAGoldCoin();
            out.println("Answer was correct!!!!");
            out.println(player.name() + " now has " + player.purse() + " Gold Coins.");
            return player.hasWon();
        }
    },
    Incorrect {
        @Override
        public boolean dealWith(Player player, PrintStream out) {
            player.moveIntoPenaltyBox();
            out.println("Question was incorrectly answered");
            out.println(player.name() + " was sent to the penalty box");
            return false;
        }
    };

    public abstract boolean dealWith(Player player, PrintStream out);
}
