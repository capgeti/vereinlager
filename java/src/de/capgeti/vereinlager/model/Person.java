package de.capgeti.vereinlager.model;

import java.io.Serializable;

/**
 * Author: capgeti
 * Date:   29.08.13 23:18
 */
public class Person implements Serializable {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
