package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Answer;

import com.adaptionsoft.games.uglytrivia.GameMaker;

import com.adaptionsoft.games.uglytrivia.Answerer;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Question;
import java.util.Random;

public class GameRunner {

    public static void main(String[] args) {
        GameMaker maker = new GameMaker(System.out);
        maker.add("Chet");
        maker.add("Pat");
        maker.add("Sue");

        Game aGame = maker.makeGame();

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
