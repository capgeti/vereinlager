package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.db.PersonDataSource;
import de.capgeti.vereinlager.db.MemberDataSource;
import de.capgeti.vereinlager.util.CustomCursorAdapter;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class PersonListFragment extends ListFragment {
    private CustomCursorAdapter adapter;
    private PersonDataSource personDataSource;
    private long memberId;

    @Override public void onPause() {
        super.onPause();
        personDataSource.close();
    }

    @Override public void onResume() {
        super.onResume();
        personDataSource.open();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MemberDataSource memberDataSource = new MemberDataSource(getActivity());

        final Cursor memberCursor = memberDataSource.detail(getArguments().getLong("memberId", -1));
        memberCursor.moveToFirst();
        memberId = memberCursor.getLong(0);

        personDataSource = new PersonDataSource(getActivity());
        final Cursor personen = personDataSource.list(memberId);

        adapter = new CustomCursorAdapter(getActivity(), R.layout.deleteable_list_item, personen) {

            @Override protected void fillView(View listItemView, final Cursor position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                ImageButton deleteButton = (ImageButton) listItemView.findViewById(R.id.deleteButton);

                lineOneView.setText(position.getString(1));
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_person, 0, 0, 0);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        personDataSource.delete(position.getLong(0));
                        refreshList();
                        Toast.makeText(PersonListFragment.this.getActivity(), "Person gelöscht!", 2).show();
                    }
                });
            }
        };

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle(memberCursor.getString(1));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_group_white);

        setListAdapter(adapter);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_list_add:
                new SimplePrompt(getActivity(), "Neue Person", "Bitte Namen eingeben:") {
                    @Override public boolean onOK(String value) {
                        if (value == null || value.isEmpty()) {
                            Toast.makeText(getActivity(), "Bitte Namen angeben!", 2).show();
                            return false;
                        }

                        personDataSource.create(value, memberId);
                        refreshList();
                        Toast.makeText(getActivity(), value + " gespeichert!", 2).show();
                        return true;
                    }
                };
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshList() {
        adapter.changeCursor(personDataSource.list(memberId));
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.person_list_menu, menu);
    }
}
