package com.adaptionsoft.games.uglytrivia;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Game {
    public static final int PLACES = 12;
    public static final int GOLD_COINS_NEEDED_TO_WIN = 6;

    private final List<Category> categories = ImmutableList.of(
            new Category("Pop"),
            new Category("Science"),
            new Category("Sports"),
            new Category("Rock")
    );
    private final Map<Category, Queue<Question>> questions = createQuestions();

    private final Iterator<Player> players;

    private final PrintStream out;

    public Game(PrintStream out, List<Player> players) {
        this.out = out;
        this.players = Iterators.cycle(ImmutableList.copyOf(players));
    }

    public boolean move(int roll, Answerer answerer) {
        Player player = players.next();
        out.println(player.name() + " is the current player");
        out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 == 0) {
                out.println(player.name() + " is not getting out of the penalty box");
                return false;
            }

            out.println(player.name() + " is getting out of the penalty box");
            player.moveOutOfPenaltyBox();
        }

        movePlayerBy(roll, player);
        return askQuestion(player, answerer);
    }

    private boolean askQuestion(Player player, Answerer answerer) {
        Category category = categories.get(player.place() % categories.size());
        Question question = questions.get(category).poll();
        category.writeTo(out);
        question.writeTo(out);
        Answer answer = answerer.answer(question);
        return answer.dealWith(player, out);
    }

    private void movePlayerBy(int roll, Player player) {
        player.incrementPlaceBy(roll);
        out.println(player.name() + "'s new location is " + player.place());
    }

    private Map<Category, Queue<Question>> createQuestions() {
        ImmutableMap.Builder<Category, Queue<Question>> questionsBuilder = ImmutableMap.builder();
        for (Category category : categories) {
            Queue<Question> categoryQuestions = new ArrayDeque<>();
            for (int i = 0; i < 50; i++) {
                categoryQuestions.add(new Question(category + " Question " + i));
            }
            questionsBuilder.put(category, categoryQuestions);
        }
        return questionsBuilder.build();
    }
}
