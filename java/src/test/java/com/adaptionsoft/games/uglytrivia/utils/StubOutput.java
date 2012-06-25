package com.adaptionsoft.games.uglytrivia.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class StubOutput {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    public PrintStream stream() {
        return out;
    }

    public Iterable<String> contents() {
        return Arrays.asList(outputStream.toString().replaceFirst("\n$", "").split("\n"));
    }

    public String contentsAsString() {
        return outputStream.toString();
    }

    public void flush() {
        outputStream.reset();
    }
}
