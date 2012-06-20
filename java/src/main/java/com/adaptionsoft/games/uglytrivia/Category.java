package com.adaptionsoft.games.uglytrivia;

public class Category {
    private final String category;

    public Category(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Category)) {
            return false;
        }

        Category other = (Category) object;
        return category.equals(other.category);
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    @Override
    public String toString() {
        return category;
    }
}
