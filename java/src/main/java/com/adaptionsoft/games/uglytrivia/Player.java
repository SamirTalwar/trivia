package com.adaptionsoft.games.uglytrivia;

public class Player {
    private final String name;
    private int place = 0;
    private int purse = 0;
    private boolean isInPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public int place() {
        return place;
    }

    public void incrementPlaceBy(int roll) {
        place += roll;
        if (place >= Game.PLACES) {
            place -= Game.PLACES;
        }
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void moveIntoPenaltyBox() {
        isInPenaltyBox = true;
    }

    public void moveOutOfPenaltyBox() {
        isInPenaltyBox = false;
    }

    public int purse() {
        return purse;
    }

    public void grantAGoldCoin() {
        purse++;
    }

    public boolean hasWon() {
        return purse == Game.GOLD_COINS_NEEDED_TO_WIN;
    }
}
