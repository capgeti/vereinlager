package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   01.10.13 23:48
 */
public class KategorieDetail {
    private final String name;
    private final String standard;

    public KategorieDetail(String name, String standard) {
        this.name = name;
        this.standard = standard;
    }

    public String getName() {
        return name;
    }

    public String getStandard() {
        return standard;
    }
}
