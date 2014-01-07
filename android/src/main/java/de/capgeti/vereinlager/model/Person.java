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

    public static String getPersonName(String name, String firstName, String nickName) {
        nickName = nickName.isEmpty() ? "" : (!firstName.isEmpty() || !name.isEmpty() ? (" (" + nickName + ")") : ("(" + nickName + ")"));
        name = name.isEmpty()? "" : (!firstName.isEmpty() ? (" " + name) : name);
        return firstName + name + nickName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
