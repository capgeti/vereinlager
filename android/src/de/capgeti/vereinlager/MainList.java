package de.capgeti.vereinlager;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class MainList extends ListFragment {
    String[] countries = new String[]{
            "Kabuff",
            "Mitglieder",
            "Export",
            "Optionen",
            "Info",
            "Beenden"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, countries);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
