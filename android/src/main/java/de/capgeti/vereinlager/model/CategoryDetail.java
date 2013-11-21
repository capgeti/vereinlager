package de.capgeti.vereinlager.model;

/**
 * Author: capgeti
 * Date:   01.10.13 23:48
 */
public class CategoryDetail {
    private final String name;
    private final String defaultValue;

    public CategoryDetail(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
