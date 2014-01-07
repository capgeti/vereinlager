package de.capgeti.vereinlager;

import android.database.Cursor;
import android.os.Bundle;

/**
 * Author: capgeti
 * Date:   21.11.13 00:38
 */
public class PersonCreateFragment extends AbstractPersonDetailFragment {

    private long memberId;

    @Override
    public String getTitleName() {
        return "Person anlegen";
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberId = getArguments().getLong("memberId");
    }

    @Override
    public void setUp() {
    }

    @Override
    public Cursor loadList() {
        return null;
    }

    @Override
    protected void savePerson(String name, String firstName, String nickName) {
        personDataSource.create(name, firstName, nickName, memberId);
    }
}
