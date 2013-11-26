package de.capgeti.vereinlager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import com.google.gson.reflect.TypeToken;
import de.capgeti.vereinlager.model.Detail;

import java.util.ArrayList;
import java.util.List;

import static de.capgeti.vereinlager.util.GsonHelper.gson;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class ElementEditFragment extends AbstractElementDetailFragment {

    private long id;
    private List<Detail> details;

    @Override public String getTitleName() {
        return "Element bearbeiten";
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        id = getArguments().getLong("elementId");
        Cursor detail = elementDataSource.detail(id);
        detail.moveToFirst();
        EditText name = (EditText) getActivity().findViewById(R.id.element_detail_name);
        name.setText(detail.getString(detail.getColumnIndex("name")));
        details = gson().fromJson(detail.getString(detail.getColumnIndex("details")), new TypeToken<List<Detail>>(){}.getType());
        detail.close();
        super.onActivityCreated(savedInstanceState);
    }

    @Override public List<Detail> loadList() {
        return details;
    }

    @Override protected void saveElement(String name, List<Detail> details) {
        elementDataSource.update(id, name, details);
    }
}
