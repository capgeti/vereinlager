package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Category {
    private final long id;
    private final String name;
    private final String itemName;

    public Category(long id, String name, String itemName) {
        this.id = id;
        this.name = name;
        this.itemName = itemName;
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return itemName;
    }

    public long getId() {
        return id;
    }
}
