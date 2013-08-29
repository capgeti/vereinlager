package de.capgeti.vereinlager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: capgeti
 * Date:   29.08.13 23:21
 */
public class Voice implements Serializable {
    private String name;
    private List<Person> persons = new ArrayList<Person>();

    public Voice(String name) {
        this.name = name;
    }

    public Voice(String name, List<Person> persons) {
        this.name = name;
        this.persons = persons;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public String getName() {
        return name;
    }
}
