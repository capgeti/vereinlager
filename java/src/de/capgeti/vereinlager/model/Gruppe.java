package de.capgeti.vereinlager.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author mwolter
 * @since 28.08.13 15:51
 */
public class Gruppe {
    private String name;
    private int aufLager;
    private int inBenutzung;
    private Map<String, Object> properties;

    public Gruppe(String name, int aufLager, int inBenutzung, Map<String, Object> properties1) {
        this.name = name;
        this.aufLager = aufLager;
        this.inBenutzung = inBenutzung;
        properties = properties1;
    }

    public int getAufLager() {
        return aufLager;
    }

    public int getInBenutzung() {
        return inBenutzung;
    }

    public String getName() {
        return name;
    }
}
