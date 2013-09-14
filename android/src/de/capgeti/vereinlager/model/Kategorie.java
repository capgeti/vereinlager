package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Kategorie {
    private final String name;
    private final Integer frei;
    private final Integer verwendet;

    public Kategorie(String name, Integer frei, Integer verwendet) {
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
}
