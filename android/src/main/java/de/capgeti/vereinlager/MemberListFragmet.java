package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;
import de.capgeti.vereinlager.db.MemberDataSource;
import de.capgeti.vereinlager.util.CustomCursorAdapter;


/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class MemberListFragmet extends ListFragment implements AdapterView.OnItemClickListener {
    private SimpleCursorAdapter adapter;
    private MemberDataSource memberDataSource;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        memberDataSource = new MemberDataSource(getActivity());
        final Cursor list = memberDataSource.list();
        adapter = new CustomCursorAdapter(getActivity(), R.layout.double_text_list, list) {
            @Override public void fillView(View parent, Cursor position) {
                TextView lineOneView = (TextView) parent.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) parent.findViewById(R.id.text2);

                lineOneView.setText(position.getString(1));
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_group, 0, 0, 0);

                lineTwoView.setText(position.getInt(2) + " Personen");
            }
        };

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Mitglieder");
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
                        inflater.inflate(R.menu.member_action_menu, menu);
                        actionMode.setTitle("Mitglieder");
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode actionMode, MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit_member_group:
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
                                new SimplePrompt(MemberListFragmet.this.getActivity(), "Gruppe bearbeiten!", "Bitte Namen eingeben:", cursor.getString(1)) {
                                    @Override public boolean onOK(String value) {
                                        if (value == null || value.isEmpty()) {
                                            Toast.makeText(MemberListFragmet.this.getActivity(), "Bitte Namen angeben!", 2).show();
                                            return false;
                                        }

                                        memberDataSource.update(cursor.getLong(0), value);
                                        refreshList();
                                        Toast.makeText(MemberListFragmet.this.getActivity(), value + " gespeichert!", 2).show();
                                        actionMode.finish();
                                        return true;
                                    }
                                };
                                return true;

                            case R.id.action_delete_member_group:
                                new AlertDialog.Builder(MemberListFragmet.this.getActivity())
                                        .setTitle("Gruppe Löschen?")
                                        .setMessage("Möchtest du die Gruppe/n wirklich löschen?\nAlle Personen und deren Verknüpfungen gehen dabei verloren!\nBenutzte Gegenstände werden wieder freigegeben.")
                                        .setPositiveButton("Ja, Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                final SparseBooleanArray pos = lv.getCheckedItemPositions();
                                                final Cursor adapterCursor = adapter.getCursor();
                                                for (int j = 0; j < lv.getCount(); j++) {
                                                    if (pos.get(j)) {
                                                        adapterCursor.moveToPosition(j);
                                                        memberDataSource.delete(adapterCursor.getLong(0));
                                                    }
                                                }
                                                refreshList();
                                                Toast.makeText(MemberListFragmet.this.getActivity(), "Gruppen gelöscht!", 2).show();
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
    }

    private void refreshList() {
        adapter.changeCursor(memberDataSource.list());
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.member_list_add:
                new SimplePrompt(getActivity(), "Neue Gruppe", "Bitte Namen eingeben:") {
                    @Override public boolean onOK(String value) {
                        if (value == null || value.isEmpty()) {
                            Toast.makeText(MemberListFragmet.this.getActivity(), "Bitte Namen angeben!", 2).show();
                            return false;
                        }

                        memberDataSource.create(value);
                        refreshList();
                        Toast.makeText(MemberListFragmet.this.getActivity(), "Gruppe " + value + " gespeichert!", 2).show();
                        return true;
                    }
                };
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.member_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override public void onPause() {
        super.onPause();
        memberDataSource.close();
    }

    @Override public void onResume() {
        super.onResume();
        memberDataSource.open();
    }

    @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        final Cursor cursor = adapter.getCursor();

        ListFragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putLong("memberId", cursor.getLong(0));
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack("person")
                .commit();

        getActivity().invalidateOptionsMenu();
    }
}
