package com.adaptionsoft.games.uglytrivia;

public class Player {
    public final String name;
    private int place = 0;
    public int purse = 0;
    public boolean inPenaltyBox = false;

    public Player(String name) {
        this.name = name;
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
}
