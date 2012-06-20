package com.adaptionsoft.games.trivia.goldenmaster;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import java.io.ByteArrayOutputStream;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class GoldenMasterTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);
    private final PrintStream originalOut = System.out;

    @Before public void
    stub_STDOUT() {
        System.setOut(out);
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
        String actualOutput = outputStream.toString();

        assertThat(actualOutput, is(expectedOutput));
    }
}
