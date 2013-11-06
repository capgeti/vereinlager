package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Kategorie {
    private final long id;
    private final String name;
    private final Integer frei;
    private final Integer verwendet;

    public Kategorie(long id, String name, Integer frei, Integer verwendet) {
        this.id = id;
        this.name = name;
        this.frei = frei;
        this.verwendet = verwendet;
    }

    public Integer getGesamt() {
        return frei + verwendet;
    }

    public String getName() {
        return name;
    }

    public Integer getFrei() {
        return frei;
    }

    public Integer getVerwendet() {
        return verwendet;
    }

    public long getId() {
        return id;
    }
}
