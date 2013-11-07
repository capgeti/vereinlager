package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.model.Stimmgruppe;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import static de.capgeti.vereinlager.StimmgruppeHandler.get;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class StimmgruppenListActivity extends ListActivity {
    List<Stimmgruppe> stimmgruppenList = new ArrayList<Stimmgruppe>();
    private CustomArrayAdapter<Stimmgruppe> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        adapter = new CustomArrayAdapter<Stimmgruppe>(this, R.layout.double_text_list, stimmgruppenList) {

            @Override protected void fillView(View listItemView, int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) listItemView.findViewById(R.id.text2);

                Stimmgruppe s = getItem(position);
                lineOneView.setText(s.getName());
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_group, 0, 0, 0);

                lineTwoView.setText(s.getPersonen() + " Personen");
            }
        };

        final ActionBar actionBar = getActionBar();
        actionBar.setTitle("Stimmgruppen");
        actionBar.setDisplayHomeAsUpEnabled(true);


        final List<Stimmgruppe> stimmgruppes = StimmgruppeHandler.get(this).listStimmgruppen();
        if(stimmgruppes != null) {
            stimmgruppenList.addAll(stimmgruppes);
            adapter.notifyDataSetChanged();
        }

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        final Intent intent = new Intent(this, PersonenListActivity.class);
        intent.putExtra("stimmgruppeId", stimmgruppenList.get(position).getId());
        startActivity(intent);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stimmgruppe_list_add:
                new SimplePrompt(this, "Neue Stimmgruppe", "Bitte Namen eingeben:") {
                    @Override public boolean onOK(String value) {
                        if (value == null || value.isEmpty()) {
                            Toast.makeText(StimmgruppenListActivity.this, "Bitte Stimmgruppe angeben!", 2).show();
                            return false;
                        }

                        final Stimmgruppe stimmgruppe = get(StimmgruppenListActivity.this).createStimmgruppe(value);
                        if (stimmgruppe != null) {
                            stimmgruppenList.add(stimmgruppe);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(StimmgruppenListActivity.this, "Stimmgruppe " + stimmgruppe.getName() + " gespeichert!", 2).show();
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stimmgruppen_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
