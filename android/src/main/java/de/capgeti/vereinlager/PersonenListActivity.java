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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Stimmgruppe;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import static de.capgeti.vereinlager.StimmgruppeHandler.get;
import static java.util.Arrays.asList;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class PersonenListActivity extends ListActivity {
    private List<Person> listItems = new ArrayList<Person>(asList(
            new Person(id, "Jens Hatzky"),
            new Person(id, "Alexander Kaschig"),
            new Person(id, "Clemens Werler"),
            new Person(id, "Michael Wolter")
    ));
    private CustomArrayAdapter<Person> adapter;
    private StimmgruppeHandler stimmgruppeHandler;
    private Stimmgruppe stimmgruppe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        adapter = new CustomArrayAdapter<Person>(this, R.layout.deleteable_list_item, listItems) {

            @Override protected void fillView(View listItemView, final int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                Button deleteButton = (Button) listItemView.findViewById(R.id.deleteButton);

                final Person s = getItem(position);
                lineOneView.setText(s.getName());

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        listItems.remove(s);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(PersonenListActivity.this, "Person gelöscht!", 2).show();
                    }
                });
            }
        };


        stimmgruppeHandler = get(this);

        stimmgruppe = stimmgruppeHandler.loadStimmgruppe(getIntent().getLongExtra("stimmgruppeId", -1));
        updateActionbarTitle();
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
    }

    private void updateActionbarTitle() {
        final ActionBar actionBar = getActionBar();
        actionBar.setTitle(stimmgruppe.getName());
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_list_add:
                new SimplePrompt(this, "Neue Person", "Bitte Namen eingeben:") {
                    @Override public boolean onOK(String value) {
                        if (value == null || value.isEmpty()) {
                            Toast.makeText(PersonenListActivity.this, "Bitte Namen angeben!", 2).show();
                            return false;
                        }

                        listItems.add(new Person(id, value));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(PersonenListActivity.this, value + " gespeichert!", 2).show();
                        return true;
                    }
                };
                break;
            case R.id.person_list_edit_stimmgruppe:
                new SimplePrompt(this, "Stimmgruppe bearbeiten!", "Bitte Namen eingeben:", stimmgruppe.getName()) {
                    @Override public boolean onOK(String value) {
                        if (value == null || value.isEmpty()) {
                            Toast.makeText(PersonenListActivity.this, "Bitte Namen angeben!", 2).show();
                            return false;
                        }
                        final Stimmgruppe update = stimmgruppeHandler.editStimmgruppe(stimmgruppe.getId(), value);
                        if (update != null) {
                            stimmgruppe = update;
                            updateActionbarTitle();
                            Toast.makeText(PersonenListActivity.this, value + " gespeichert!", 2).show();
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
                break;
            case R.id.person_list_delete_stimmgruppe:
                new AlertDialog.Builder(this)
                        .setTitle("Stimmgruppe Löschen?")
                        .setMessage("Möchtest du die Stimmgruppe wirklich löschen?\nAlle Personen und deren Verknüpfungen gehen dabei verloren!\nBenutzte Gegenstände werden wieder freigegeben.")
                        .setPositiveButton("Ja, Okay", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                get(PersonenListActivity.this).deleteStimmgruppe(stimmgruppe.getId());
                                Toast.makeText(PersonenListActivity.this, "Stimmgruppe gelöscht!", 2).show();
                                navigateUpTo(new Intent(PersonenListActivity.this, StimmgruppenListActivity.class));
                            }
                        })
                        .setNegativeButton("Ne doch nicht", null).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
