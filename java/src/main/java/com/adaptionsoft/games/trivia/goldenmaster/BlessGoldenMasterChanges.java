package com.adaptionsoft.games.trivia.goldenmaster;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class BlessGoldenMasterChanges {
    public static void main(String[] args) throws IOException {
        for (File goldenMaster : new File("goldenmaster").listFiles()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outputStream);
            System.setOut(out);
            GameRunner.main(new String[] {goldenMaster.getName().replaceFirst("\\.out$", "")});
            String newOutput = outputStream.toString();

            try (FileWriter writer = new FileWriter(goldenMaster)) {
                writer.write(newOutput);
            }
        }
    }
}
