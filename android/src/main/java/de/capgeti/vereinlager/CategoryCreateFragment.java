package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Detail;

import java.util.ArrayList;
import java.util.List;

/**
* Author: capgeti
* Date:   21.11.13 00:38
*/
public class CategoryCreateFragment extends AbstractCategoryDetailFragment {

    @Override public String getTitleName() {
        return "Kategorie anlegen";
    }

    @Override protected void setUp() {
    }

    @Override public List<Detail> loadList() {
        return new ArrayList<Detail>();
    }

    @Override protected void saveCategory(String name, List<Detail> details) {
        categoryDataSource.create(name, details);
    }
}
