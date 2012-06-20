package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Answerer;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Question;
import java.util.Random;

public class GameRunner {

    public static void main(String[] args) {
        Game aGame = new Game(System.out);

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        final Random rand = args.length > 0 ? new Random(Integer.valueOf(args[0])) : new Random();
        boolean winner;
        do {
            winner = aGame.move(rand.nextInt(5) + 1, new Answerer() {
                @Override public Answer answer(Question question) {
                    if (rand.nextInt(9) == 7) {
                        return Answer.Incorrect;
                    } else {
                        return Answer.Correct;
                    }
                }
            });
        } while (!winner);
    }
}
