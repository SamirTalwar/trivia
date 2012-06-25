package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GameMaker {
    private final PrintStream out;
    private final List<Player> players = new ArrayList<>();

    public GameMaker(PrintStream out) {
        this.out = out;
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        out.println(playerName + " was added");
        out.println("They are player number " + players.size());
        return true;
    }

    public Game makeGame() {
        return new Game(out, players);
    }
}
