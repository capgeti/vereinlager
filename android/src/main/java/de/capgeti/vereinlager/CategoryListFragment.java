package de.capgeti.vereinlager;

import android.app.*;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;
import de.capgeti.vereinlager.db.CategoryDataSource;
import de.capgeti.vereinlager.util.CustomCursorAdapter;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class CategoryListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private CategoryDataSource categoryDataSource;
    private CustomCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        categoryDataSource = new CategoryDataSource(getActivity());

        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Kategorien");
        actionBar.setIcon(R.drawable.ic_launcher);

        Cursor list = categoryDataSource.list();
        adapter = new CustomCursorAdapter(getActivity(), R.layout.double_text_list, list) {

            @Override protected void fillView(View listItemView, Cursor position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) listItemView.findViewById(R.id.text2);

                lineOneView.setText(position.getString(position.getColumnIndex("name")));
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_storage, 0, 0, 0);

                long fullCount = position.getLong(position.getColumnIndex("elements"));
                long used = position.getLong(position.getColumnIndex("used"));
                lineTwoView.setText(fullCount + " gesamt ( "+ used + " verwendet )");
            }
        };
        setListAdapter(adapter);

        final ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        final AbsListView.MultiChoiceModeListener multiChoiceModeListener = new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                final int count = lv.getCheckedItemCount();
                actionMode.setSubtitle(count + " ausgewählt");
                actionMode.getMenu().getItem(0).setVisible(count < 2);
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.simple_action_menu, menu);
                actionMode.setTitle("Kategorien");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
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

                        Fragment fragment = new CategoryEditFragment();
                        Bundle args = new Bundle();
                        args.putLong("categoryId", cursor.getLong(cursor.getColumnIndex("id")));
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .addToBackStack("kategorie")
                                .commit();
                        getActivity().invalidateOptionsMenu();

                        actionMode.finish();
                        return true;

                    case R.id.action_delete:
                        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light))
                                .setTitle("Kategorie Löschen?")
                                .setMessage("Möchtest du die Kategorie/n wirklich löschen?\nAlle Elemente und Verknüpfungen zu Personen gehen dabei verloren!")
                                .setPositiveButton("Ja, Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        final SparseBooleanArray pos = lv.getCheckedItemPositions();
                                        final Cursor adapterCursor = adapter.getCursor();
                                        for (int j = 0; j < lv.getCount(); j++) {
                                            if (pos.get(j)) {
                                                adapterCursor.moveToPosition(j);
                                                categoryDataSource.delete(adapterCursor.getLong(0));
                                            }
                                        }
                                        refreshList();
                                        Toast.makeText(getActivity(), "Kategorie/n gelöscht!", 2).show();
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

        refreshList();
    }

    @Override public void onPause() {
        super.onPause();
        categoryDataSource.close();
    }

    @Override public void onResume() {
        super.onResume();
        categoryDataSource.open();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kategorie_list_add:
                Fragment fragment = new CategoryCreateFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack("kategorie")
                        .commit();
                getActivity().invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.kategorie_list_menu, menu);
    }

    @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ListFragment fragment = new ElementListFragment();
        Bundle args = new Bundle();
        args.putLong("categoryId", id);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack("elemets")
                .commit();
        getActivity().invalidateOptionsMenu();
    }

    private void refreshList() {
        adapter.changeCursor(categoryDataSource.list());
    }
}
