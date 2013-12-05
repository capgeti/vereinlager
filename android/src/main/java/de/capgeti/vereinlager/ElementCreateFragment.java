package de.capgeti.vereinlager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import com.google.gson.reflect.TypeToken;
import de.capgeti.vereinlager.db.CategoryDataSource;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static de.capgeti.vereinlager.util.GsonHelper.gson;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class ElementCreateFragment extends AbstractElementDetailFragment {

    private long categoryId;
    private CategoryDataSource categoryDataSource;
    private List<Detail> details;

    @Override public String getTitleName() {
        return "Element anlegen";
    }

    @Override public void onPause() {
        super.onPause();
        categoryDataSource.close();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getLong("categoryId");
    }

    @Override public void setUp() {
        categoryDataSource = new CategoryDataSource(getActivity());

        Cursor detail = categoryDataSource.detail(categoryId);
        detail.moveToFirst();

        EditText name = (EditText) getActivity().findViewById(R.id.element_detail_name);
        name.setText(detail.getString(detail.getColumnIndex("itemName")));

        details = gson().fromJson(detail.getString(detail.getColumnIndex("details")), new TypeToken<List<Detail>>() {
        }.getType());
        detail.close();
    }

    @Override public List<Detail> loadList() {
        return details;
    }

    @Override protected Long saveElement(String name, List<Detail> details) {
        return elementDataSource.create(categoryId, name, details);
    }
}
