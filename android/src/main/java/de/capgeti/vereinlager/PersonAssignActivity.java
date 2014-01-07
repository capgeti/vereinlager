package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.capgeti.vereinlager.db.PersonDataSource;
import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.util.CustomCursorAdapter;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class PersonAssignActivity extends ListActivity {
    private PersonDataSource personDataSource;

    @Override
    public void onPause() {
        super.onPause();
        personDataSource.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personDataSource = new PersonDataSource(this);

        final Cursor personen = personDataSource.list(null);

        CustomCursorAdapter adapter = new CustomCursorAdapter(this, R.layout.person_assign_list_item, personen) {

            @Override
            protected void fillView(final View listItemView, final Cursor position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                String name = position.getString(position.getColumnIndex("name"));
                String firstName = position.getString(position.getColumnIndex("firstName"));
                String nickName = position.getString(position.getColumnIndex("nickName"));
                lineOneView.setText(Person.getPersonName(name, firstName, nickName));
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_person, 0, 0, 0);
            }
        };

        final ActionBar actionBar = getActionBar();
        actionBar.setTitle("Person zuweisen");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_labels_white);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent data = new Intent();
        data.putExtra("personId", id);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cancel_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
