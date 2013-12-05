package de.capgeti.vereinlager;

import android.database.Cursor;
import android.widget.EditText;
import com.google.gson.reflect.TypeToken;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static de.capgeti.vereinlager.util.GsonHelper.gson;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class CategoryEditFragment extends AbstractCategoryDetailFragment {

    private long id;
    private List<Detail> details;

    @Override public String getTitleName() {
        return "Kategorie bearbeiten";
    }

    @Override public void setUp() {
        id = getArguments().getLong("categoryId");
        Cursor detail = categoryDataSource.detail(id);
        detail.moveToFirst();
        EditText name = (EditText) getActivity().findViewById(R.id.category_detail_name);
        name.setText(detail.getString(detail.getColumnIndex("name")));

        EditText defaultName = (EditText) getActivity().findViewById(R.id.category_detail_item_name);
        defaultName.setText(detail.getString(detail.getColumnIndex("itemName")));

        details = gson().fromJson(detail.getString(detail.getColumnIndex("details")), new TypeToken<List<Detail>>() {
        }.getType());
        detail.close();
    }

    @Override public List<Detail> loadList() {
        return details;
    }

    @Override protected void saveCategory(String name, String itemName, List<Detail> details) {
        categoryDataSource.update(id, name, itemName, details);
    }
}
