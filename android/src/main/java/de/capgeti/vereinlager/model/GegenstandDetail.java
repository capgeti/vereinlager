package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   01.10.13 23:48
 */
public class GegenstandDetail {
    private final String name;
    private final String wert;

    public GegenstandDetail(String name, String wert) {
        this.name = name;
        this.wert = wert;
    }

    public String getName() {
        return name;
    }

    public String getWert() {
        return wert;
    }
}
