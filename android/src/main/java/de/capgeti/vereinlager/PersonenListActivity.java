package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.db.PersonDataSource;
import de.capgeti.vereinlager.db.StimmgruppeDataSource;
import de.capgeti.vereinlager.util.CustomCursorAdapter;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class PersonenListActivity extends ListActivity {
    private CustomCursorAdapter adapter;
    private PersonDataSource personDataSource;
    private long stimmgruppeId;

    @Override protected void onPause() {
        super.onPause();
        personDataSource.close();
    }

    @Override protected void onResume() {
        super.onResume();
        personDataSource.open();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StimmgruppeDataSource stimmgruppeDataSource = new StimmgruppeDataSource(this);
        final Cursor stimmgruppeCursor = stimmgruppeDataSource.detail(getIntent().getLongExtra("stimmgruppeId", -1));
        stimmgruppeCursor.moveToFirst();
        stimmgruppeId = stimmgruppeCursor.getLong(0);

        personDataSource = new PersonDataSource(this);
        final Cursor personen = personDataSource.list(stimmgruppeId);

        adapter = new CustomCursorAdapter(this, R.layout.deleteable_list_item, personen) {

            @Override protected void fillView(View listItemView, final Cursor position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                ImageButton deleteButton = (ImageButton) listItemView.findViewById(R.id.deleteButton);

                lineOneView.setText(position.getString(1));
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_person, 0, 0, 0);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        personDataSource.delete(position.getLong(0));
                        refreshList();
                        Toast.makeText(PersonenListActivity.this, "Person gelöscht!", 2).show();
                    }
                });
            }
        };

        final ActionBar actionBar = getActionBar();
        actionBar.setTitle(stimmgruppeCursor.getString(1));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
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

                        personDataSource.create(value, stimmgruppeId);
                        refreshList();
                        Toast.makeText(PersonenListActivity.this, value + " gespeichert!", 2).show();
                        return true;
                    }
                };
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshList() {
        adapter.changeCursor(personDataSource.list(stimmgruppeId));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
