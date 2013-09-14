package de.capgeti.vereinlager;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import de.capgeti.vereinlager.model.Gegenstand;
import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class GegenstandList extends ListFragment {
    Gegenstand[] countries = new Gegenstand[]{
            new Gegenstand("Lefima", map("Nr", 1), new Person("Clemens Werler")),
            new Gegenstand("Lefima", map("Nr", 2), null),
            new Gegenstand("Lefima", map("Nr", 3), new Person("Lucas Reinhardt")),
            new Gegenstand("Lefima", map("Nr", 7), new Person("Michael Wolter"))
    };
    private boolean sumView = false;

    private Map<String, Object> map(Object... objects) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < objects.length; i += 2) {
            map.put((String) objects[i], objects[i + 1]);
        }
        return map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CustomArrayAdapter<Gegenstand> adapter = new CustomArrayAdapter<Gegenstand>(inflater.getContext(), R.layout.gegenstand_list, countries) {
            @Override protected void fillView(View listItemView, int position) {
                TextView name = (TextView) listItemView.findViewById(R.id.gegenstandName);
                TextView besitzer = (TextView) listItemView.findViewById(R.id.gegenstandBesitzer);
                TextView params = (TextView) listItemView.findViewById(R.id.gegenStandParams);

                Gegenstand s = getItem(position);
                name.setText(s.getName());
                besitzer.setText(s.getBesitzer() != null ? "-> " + s.getBesitzer().getName() : "");

                String paramString = "";
                for (Map.Entry<String, Object> entry : s.getParams().entrySet()) {
                    paramString += entry.getKey() + ": " + entry.getValue() + " ";
                }
                params.setText(paramString);
            }

        };

        setHasOptionsMenu(this.equals(getFragmentManager().findFragmentById(R.id.secondFragment)));

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gegenstand_list_sum_toogle:
                sumView = !sumView;
                item.setTitle(sumView ? "Einzeln" : "Summieren");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gegenstand_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
