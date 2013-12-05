package de.capgeti.vereinlager;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static de.capgeti.vereinlager.util.GsonHelper.gson;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class ElementEditFragment extends AbstractElementDetailFragment {

    private long id;
    private List<Detail> details;

    @Override
    public String getTitleName() {
        return "Element bearbeiten";
    }

    @Override
    public void setUp() {
        id = getArguments().getLong("elementId");
        Cursor detail = elementDataSource.detail(id);
        detail.moveToFirst();
        EditText name = (EditText) getActivity().findViewById(R.id.element_detail_name);
        name.setText(detail.getString(detail.getColumnIndex("name")));
        details = gson().fromJson(detail.getString(detail.getColumnIndex("details")), new TypeToken<List<Detail>>() {
        }.getType());

        long tmpPerson = detail.getLong(detail.getColumnIndex("personId"));
        Long personId = tmpPerson == 0 ? null : tmpPerson;
        updatePersonHandling(personId);

        View panel = getActivity().findViewById(R.id.element_detail_person_assign_panel);
        panel.setVisibility(VISIBLE);


        detail.close();
    }

    @Override
    public List<Detail> loadList() {
        return details;
    }

    @Override
    protected Long saveElement(String name, List<Detail> details) {
        elementDataSource.update(id, name, details);
        return id;
    }
}
