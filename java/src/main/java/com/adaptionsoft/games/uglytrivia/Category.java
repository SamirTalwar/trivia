package com.adaptionsoft.games.uglytrivia;

public class Category {
    private final String category;

    public Category(String category) {
        this.category = category;
    }

    public boolean is(String string) {
        return category.equals(string);
    }

    @Override
    public String toString() {
        return category;
    }
}
