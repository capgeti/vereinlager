package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.capgeti.vereinlager.model.Kategorie;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class KategorieListActivity extends ListActivity {
    List<Kategorie> countries = asList(new Kategorie(0, "Lefima Alt", 4, 10));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        CustomArrayAdapter<Kategorie> adapter = new CustomArrayAdapter<Kategorie>(this, R.layout.double_text_list, countries) {

            @Override protected void fillView(View listItemView, int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) listItemView.findViewById(R.id.text2);

                Kategorie s = getItem(position);
                lineOneView.setText(s.getName());
                lineTwoView.setText(s.getGesamt() + " gesamt (" + s.getFrei() + " frei / " + s.getVerwendet() + " verwendet )");
            }
        };

        final ActionBar actionBar = getActionBar();
        actionBar.setTitle("Kategorien");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        final Intent intent = new Intent(this, GegenstandListActivity.class);
        intent.putExtra("kategorie", countries.get(position).getName());
        startActivity(intent);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kategorie_list_add:
                final Intent intent = new Intent(this, KategorieDetailActivity.class);
                intent.putExtra("create", true);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kategorie_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
