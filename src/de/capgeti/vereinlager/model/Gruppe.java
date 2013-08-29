package de.capgeti.vereinlager.model;

/**
 * @author mwolter
 * @since 28.08.13 15:51
 */
public class Gruppe {
    private String name;
    private int aufLager;
    private int inBenutzung;

    public Gruppe(String name, int aufLager, int inBenutzung) {
        this.name = name;
        this.aufLager = aufLager;
        this.inBenutzung = inBenutzung;
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
