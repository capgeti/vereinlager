package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   01.10.13 23:48
 */
public class Detail {
    private String name;
    private String value;

    public Detail(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
