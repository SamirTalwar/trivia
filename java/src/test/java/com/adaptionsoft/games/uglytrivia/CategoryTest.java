package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CategoryTest {
    @Test public void
    a_category_matches_against_a_string_containing_its_name() {
        assertThat(new Category("foo").is("foo"), is(true));
    }

    @Test public void
    a_category_does_not_match_against_any_other_string() {
        assertThat(new Category("foo").is("bar"), is(false));
    }
}
