package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Category {
    private final long id;
    private final String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
