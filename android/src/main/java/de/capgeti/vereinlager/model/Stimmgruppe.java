package de.capgeti.vereinlager.model;

import de.capgeti.vereinlager.StimmgruppeHandler;
import org.mapdb.BTreeMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class Stimmgruppe implements Serializable {
    private Long id;
    private String name;
    private Integer personen;

    public Stimmgruppe() {
    }

    public Stimmgruppe(Long id, String name, Integer personen) {
        this.id = id;
        this.name = name;
        this.personen = personen;
    }

    public Integer getPersonen() {
        return personen;
    }

    public void setPersonen(Integer personen) {
        this.personen = personen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
