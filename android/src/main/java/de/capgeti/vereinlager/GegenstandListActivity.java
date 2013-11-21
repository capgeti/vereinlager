package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.model.Gegenstand;
import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.*;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class GegenstandListActivity extends ListActivity {
    List<Gegenstand> countries = new ArrayList<Gegenstand>(Arrays.asList(
            new Gegenstand(0, "Lefima", map("Nr", 1), new Person(1L, "Clemens Werler")),
            new Gegenstand(1, "Lefima", map("Nr", 2), null),
            new Gegenstand(2, "Lefima", map("Nr", 3), new Person(2L, "Lucas Reinhardt")),
            new Gegenstand(3, "Lefima", map("Nr", 7), new Person(3L, "Michael Wolter"))));
    private boolean sumView = false;

    private Map<String, Object> map(Object... objects) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < objects.length; i += 2) {
            map.put((String) objects[i], objects[i + 1]);
        }
        return map;
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        final Intent intent = new Intent(this, GegenstandDetailActivity.class);
        intent.putExtra("create", false);
        intent.putExtra("gegenstand", countries.get(position).getName());
        startActivity(intent);
    }


    @Override protected void onCreate(Bundle savedInstanceState) {

        CustomArrayAdapter<Gegenstand> adapter = new CustomArrayAdapter<Gegenstand>(this, R.layout.gegenstand_list, countries) {
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

        setListAdapter(adapter);
        final ActionBar actionBar = getActionBar();
        actionBar.setTitle(getIntent().getStringExtra("kategorie"));
        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gegenstand_list_add :
                Intent gegenstandDetail = new Intent(this, GegenstandDetailActivity.class);
                gegenstandDetail.putExtra("create", true);
                startActivity(gegenstandDetail);
                break;
            case R.id.gegenstand_list_sum_toogle:
                sumView = !sumView;
                item.setTitle(sumView ? "Einzelliste" : "Summierte Liste");
                break;
            case R.id.gegenstand_list_edit_kategorie:
                Intent kategorieDetail = new Intent(this, AbstractCategoryDetailFragment.class);
                kategorieDetail.putExtra("create", false);
                kategorieDetail.putExtra("kategorie", "TODO");
                startActivity(kategorieDetail);
                break;
            case R.id.gegenstand_list_delete_kategorie:
                new AlertDialog.Builder(this)
                        .setTitle("Kategorie Löschen?")
                        .setMessage("Möchtest du die Kategorie wirklich löschen?\nAlle Verknüpfungen gehen dabei verloren!")
                        .setPositiveButton("Ja, Okay", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(GegenstandListActivity.this, "Kategorie gelöscht!", 2).show();
                            }
                        })
                        .setNegativeButton("Ne doch nicht", null).create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gegenstand_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
