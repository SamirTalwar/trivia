package com.adaptionsoft.games.trivia.goldenmaster;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.utils.StubOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Theories.class)
public class GoldenMasterTest {
    private final StubOutput output = new StubOutput();
    private final PrintStream originalOut = System.out;

    @Before public void
    stub_STDOUT() {
        System.setOut(output.stream());
    }

    @After public void
    reset_STDOUT() {
        System.setOut(originalOut);
    }

    public static final @DataPoints File[] goldenMasters;
    static {
        goldenMasters = new File("goldenmaster").listFiles();
    }

    @Theory public void
    check_against_the_golden_master(File goldenMaster) throws FileNotFoundException, IOException {
        byte[] expectedOutputAsByteArray = new byte[(int) goldenMaster.length()];
        try (FileInputStream inputStream = new FileInputStream(goldenMaster)) {
            inputStream.read(expectedOutputAsByteArray);
        }
        String expectedOutput = new String(expectedOutputAsByteArray);

        GameRunner.main(new String[] {goldenMaster.getName().replaceFirst("\\.out$", "")});
        String actualOutput = output.contentsAsString();

        assertThat(actualOutput, is(expectedOutput));
    }
}
