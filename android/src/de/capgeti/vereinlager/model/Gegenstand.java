package de.capgeti.vereinlager.model;

import java.util.Map;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Gegenstand {
    private final String name;
    private final Map<String, Object> params;
    private final Person besitzer;

    public Gegenstand(String name, Map<String, Object> params, Person besitzer) {
        this.name = name;
        this.params = params;
        this.besitzer = besitzer;
    }

    public Person getBesitzer() {
        return besitzer;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
