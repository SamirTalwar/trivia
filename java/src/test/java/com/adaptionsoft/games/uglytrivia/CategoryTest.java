package com.adaptionsoft.games.uglytrivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class CategoryTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    @Test public void
    a_category_is_equal_to_those_with_the_same_name() {
        Category a = new Category("foo");
        Category b = new Category("foo");
        assertThat(a, is(equalTo(b)));
        assertThat(a.hashCode(), is(equalTo(b.hashCode())));
    }

    @Test public void
    a_category_is_not_equal_to_any_other_category() {
        Category a = new Category("foo");
        Category b = new Category("bar");
        assertThat(a, is(not(equalTo(b))));
        assertThat(a.hashCode(), is(not(equalTo(b.hashCode()))));
    }

    @Test public void
    writes_itself() {
        Category category = new Category("dogegory");
        category.writeTo(out);

        assertThat(output(), contains("The category is dogegory"));
    }

    private Iterable<String> output() {
        return Arrays.asList(outputStream.toString().replaceFirst("\n$", "").split("\n"));
    }
}
