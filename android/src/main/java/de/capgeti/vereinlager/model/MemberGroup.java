package de.capgeti.vereinlager.model;

import java.io.Serializable;

/**
 * Author: capgeti
 * Date:   11.09.13 00:09
 */
public class MemberGroup implements Serializable {
    private Long id;
    private String name;
    private Integer personen;

    public MemberGroup() {
    }

    public MemberGroup(Long id, String name, Integer personen) {
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
