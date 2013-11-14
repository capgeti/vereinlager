package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;
import de.capgeti.vereinlager.db.StimmgruppeDataSource;
import de.capgeti.vereinlager.util.CustomCursorAdapter;


/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class StimmgruppenListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private SimpleCursorAdapter adapter;
    private StimmgruppeDataSource stimmgruppeDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        stimmgruppeDataSource = new StimmgruppeDataSource(this);
        final Cursor list = stimmgruppeDataSource.list();
        adapter = new CustomCursorAdapter(this, R.layout.double_text_list, list) {
            @Override public void fillView(View parent, Cursor position) {
                TextView lineOneView = (TextView) parent.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) parent.findViewById(R.id.text2);

                lineOneView.setText(position.getString(1));
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_group, 0, 0, 0);

                lineTwoView.setText(position.getInt(2) + " Personen");
            }
        };

        final ActionBar actionBar = getActionBar();
        actionBar.setTitle("Stimmgruppen");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setListAdapter(adapter);

        final ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        final AbsListView.MultiChoiceModeListener multiChoiceModeListener = new

                AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                        final int count = lv.getCheckedItemCount();
                        actionMode.setSubtitle(count + " ausgewählt");
                        actionMode.getMenu().getItem(0).setVisible(count < 2);
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        MenuInflater inflater = actionMode.getMenuInflater();
                        inflater.inflate(R.menu.stimmgruppen_action_menu, menu);
                        actionMode.setTitle("Stimmgruppen");
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode actionMode, MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit_stimmgruppe:
                                final SparseBooleanArray positions = lv.getCheckedItemPositions();
                                int pos = -1;
                                for (int i = 0; i < lv.getCount(); i++) {
                                    if (positions.get(i)) {
                                        pos = i;
                                        break;
                                    }
                                }
                                final Cursor cursor = adapter.getCursor();
                                cursor.moveToPosition(pos);
                                new SimplePrompt(StimmgruppenListActivity.this, "Stimmgruppe bearbeiten!", "Bitte Namen eingeben:", cursor.getString(1)) {
                                    @Override public boolean onOK(String value) {
                                        if (value == null || value.isEmpty()) {
                                            Toast.makeText(StimmgruppenListActivity.this, "Bitte Namen angeben!", 2).show();
                                            return false;
                                        }

                                        stimmgruppeDataSource.update(cursor.getLong(0), value);
                                        refreshList();
                                        Toast.makeText(StimmgruppenListActivity.this, value + " gespeichert!", 2).show();
                                        actionMode.finish();
                                        return true;
                                    }
                                };
                                return true;

                            case R.id.action_delete_stimmgruppe:
                                new AlertDialog.Builder(StimmgruppenListActivity.this)
                                        .setTitle("Stimmgruppe Löschen?")
                                        .setMessage("Möchtest du die Stimmgruppe/n wirklich löschen?\nAlle Personen und deren Verknüpfungen gehen dabei verloren!\nBenutzte Gegenstände werden wieder freigegeben.")
                                        .setPositiveButton("Ja, Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                final SparseBooleanArray pos = lv.getCheckedItemPositions();
                                                final Cursor adapterCursor = adapter.getCursor();
                                                for (int j = 0; j < lv.getCount(); j++) {
                                                    if (pos.get(j)) {
                                                        adapterCursor.moveToPosition(j);
                                                        stimmgruppeDataSource.delete(adapterCursor.getLong(0));
                                                    }
                                                }
                                                refreshList();
                                                Toast.makeText(StimmgruppenListActivity.this, "Stimmgruppen gelöscht!", 2).show();
                                                actionMode.finish();
                                            }
                                        })
                                        .setNegativeButton("Ne doch nicht", null).create().show();

                        }
                        return false;
                    }

                    @Override public void onDestroyActionMode(ActionMode actionMode) {
                    }
                };
        lv.setMultiChoiceModeListener(multiChoiceModeListener);

        lv.setOnItemClickListener(this);

        super.onCreate(savedInstanceState);
    }

    private void refreshList() {
        adapter.changeCursor(stimmgruppeDataSource.list());
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

                        stimmgruppeDataSource.create(value);
                        refreshList();
                        Toast.makeText(StimmgruppenListActivity.this, "Stimmgruppe " + value + " gespeichert!", 2).show();
                        return true;
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

    @Override protected void onPause() {
        super.onPause();
        stimmgruppeDataSource.close();
    }

    @Override protected void onResume() {
        super.onResume();
        stimmgruppeDataSource.open();
    }

    @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        final Intent intent = new Intent(this, PersonenListActivity.class);
        final Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);
        intent.putExtra("stimmgruppeId", cursor.getLong(0));
        startActivity(intent);
    }
}
