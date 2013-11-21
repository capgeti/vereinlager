package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.CategoryDetail;

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

    @Override public List<CategoryDetail> loadList() {
        return new ArrayList<CategoryDetail>();
    }

    @Override protected void saveCategory(String name, String itemName, List<CategoryDetail> details) {
        categoryDataSource.create(name, itemName, details);
    }
}
