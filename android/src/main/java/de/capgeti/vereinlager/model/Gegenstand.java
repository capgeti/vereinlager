package de.capgeti.vereinlager.model;

import java.util.Map;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Gegenstand {
    private final long id;
    private final String name;
    private final Map<String, Object> params;
    private final Person besitzer;

    public Gegenstand(long id, String name, Map<String, Object> params, Person besitzer) {
        this.id = id;
        this.name = name;
        this.params = params;
        this.besitzer = besitzer;
    }

    public long getId() {
        return id;
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
