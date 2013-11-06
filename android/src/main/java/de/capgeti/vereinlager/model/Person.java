package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   14.09.13 02:14
 */
public class Person {
    private final String name;
    private Long id;

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
