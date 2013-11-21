package de.capgeti.vereinlager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import de.capgeti.vereinlager.model.CategoryDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class CategoryEditFragment extends AbstractCategoryDetailFragment {

    private long id;

    @Override public String getTitleName() {
        return "Kategorie bearbeiten";
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        id = getArguments().getLong("categoryId");
        Cursor detail = categoryDataSource.detail(id);
        detail.moveToFirst();
        EditText name = (EditText) getActivity().findViewById(R.id.kategorie_detail_name);
        EditText itemName = (EditText) getActivity().findViewById(R.id.kategorie_detail_gegenstand_std_name);
        name.setText(detail.getString(detail.getColumnIndex("name")));
        itemName.setText(detail.getString(detail.getColumnIndex("itemName")));
        super.onActivityCreated(savedInstanceState);
    }

    @Override public List<CategoryDetail> loadList() {
        Cursor cursor = categoryDataSource.listDetails(id);
        ArrayList<CategoryDetail> categoryDetails = new ArrayList<CategoryDetail>();
        while (cursor.moveToNext()) {
            categoryDetails.add(new CategoryDetail(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("defaultValue"))
            ));
        }
        return categoryDetails;
    }

    @Override protected void saveCategory(String name, String itemName, List<CategoryDetail> details) {
        categoryDataSource.update(id, name, itemName, details);
    }
}
