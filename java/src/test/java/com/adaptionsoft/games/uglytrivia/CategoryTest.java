package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class CategoryTest {
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
}
