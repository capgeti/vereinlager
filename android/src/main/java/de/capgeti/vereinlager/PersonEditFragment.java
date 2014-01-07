package de.capgeti.vereinlager;

import android.database.Cursor;
import android.widget.EditText;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class PersonEditFragment extends AbstractPersonDetailFragment {

    private long id;

    @Override
    public String getTitleName() {
        return "Person bearbeiten";
    }

    @Override
    public void setUp() {
        id = getArguments().getLong("personId");
        Cursor detail = personDataSource.detail(id);
        detail.moveToFirst();

        EditText name = (EditText) getActivity().findViewById(R.id.name);
        name.setText(detail.getString(detail.getColumnIndex("name")));

        EditText firstName = (EditText) getActivity().findViewById(R.id.firstName);
        firstName.setText(detail.getString(detail.getColumnIndex("firstName")));

        EditText nickName = (EditText) getActivity().findViewById(R.id.nickName);
        nickName.setText(detail.getString(detail.getColumnIndex("nickName")));

        detail.close();
    }

    @Override
    public Cursor loadList() {
        return elementDataSource.listForPerson(id);
    }

    @Override
    protected void savePerson(String name, String firstName, String nickName) {
        personDataSource.update(id, name, firstName, nickName);
    }
}
