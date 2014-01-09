package de.capgeti.vereinlager;

import android.app.*;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;
import com.google.gson.reflect.TypeToken;
import de.capgeti.vereinlager.db.CategoryDataSource;
import de.capgeti.vereinlager.db.ElementDataSource;
import de.capgeti.vereinlager.model.Detail;
import de.capgeti.vereinlager.util.CustomCursorAdapter;
import de.capgeti.vereinlager.util.GsonHelper;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static de.capgeti.vereinlager.model.Person.getPersonName;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class ElementListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private boolean sumView = false;
    private ElementDataSource elementDataSource;
    private CategoryDataSource categoryDataSource;
    private long categoryId;
    private CustomCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        elementDataSource = new ElementDataSource(getActivity());
        categoryDataSource = new CategoryDataSource(getActivity());

        categoryId = getArguments().getLong("categoryId");

        adapter = new CustomCursorAdapter(getActivity(), R.layout.element_list_item, elementDataSource.listForCategory(categoryId, sumView)) {
            @Override
            protected void fillView(View listItemView, Cursor position) {
                TextView name = (TextView) listItemView.findViewById(R.id.elementName);
                TextView besitzer = (TextView) listItemView.findViewById(R.id.elementOwner);
                TextView params = (TextView) listItemView.findViewById(R.id.elementValues);

                name.setText(position.getString(position.getColumnIndex("name")));
                name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_labels, 0, 0, 0);

                String details = position.getString(position.getColumnIndex("details"));
                List<Detail> detailList = GsonHelper.gson().fromJson(details, new TypeToken<List<Detail>>() {
                }.getType());

                String paramString = "";
                for (Detail detail : detailList) {
                    paramString += detail.getName() + ": " + detail.getValue() + " ";
                }
                params.setText(paramString);

                if (sumView) {
                    int all = position.getInt(position.getColumnIndex("groupCount"));
                    int used = position.getInt(position.getColumnIndex("used"));
                    besitzer.setText(all + " gesamt ( " + (all - used) + " frei / " + used + " verwendet )");
                    besitzer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    String personName = position.getString(position.getColumnIndex("personName"));
                    String personFirstName = position.getString(position.getColumnIndex("personFirstName"));
                    String personNickName = position.getString(position.getColumnIndex("personNickName"));
                    if (personName != null && personFirstName != null && personNickName != null) {
                        besitzer.setText(getPersonName(personName, personFirstName, personNickName));
                        besitzer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_person_small, 0, 0, 0);
                    }
                }
            }

        };

        setListAdapter(adapter);
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_storage_white);

        Cursor categoryCursor = categoryDataSource.detail(categoryId);
        categoryCursor.moveToFirst();
        actionBar.setTitle(categoryCursor.getString(categoryCursor.getColumnIndex("name")));
        categoryCursor.close();

        final ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        final AbsListView.MultiChoiceModeListener multiChoiceModeListener = new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                final int count = lv.getCheckedItemCount();
                actionMode.setSubtitle(count + " ausgewählt");
                actionMode.getMenu().findItem(R.id.action_edit).setVisible(count < 2);
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                if (sumView) return false;
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.simple_action_menu, menu);
                actionMode.setTitle("Elemente");
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

                        Fragment fragment = new ElementEditFragment();
                        Bundle args = new Bundle();
                        args.putLong("elementId", cursor.getLong(cursor.getColumnIndex("id")));
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .addToBackStack("element")
                                .commit();
                        getActivity().invalidateOptionsMenu();

                        actionMode.finish();
                        return true;

                    case R.id.action_delete:
                        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light))
                                .setTitle("Element Löschen?")
                                .setMessage("Möchtest du das/die Element/e wirklich löschen?\nAlle Verknüpfungen zu Personen gehen dabei verloren!")
                                .setPositiveButton("Ja, Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        final SparseBooleanArray pos = lv.getCheckedItemPositions();
                                        final Cursor adapterCursor = adapter.getCursor();
                                        for (int j = 0; j < lv.getCount(); j++) {
                                            if (pos.get(j)) {
                                                adapterCursor.moveToPosition(j);
                                                elementDataSource.delete(adapterCursor.getLong(0));
                                            }
                                        }
                                        refreshList();
                                        Toast.makeText(getActivity(), "Element/e gelöscht!", LENGTH_SHORT).show();
                                        actionMode.finish();
                                    }
                                })
                                .setNegativeButton("Ne doch nicht", null).create().show();

                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        };
        lv.setMultiChoiceModeListener(multiChoiceModeListener);
        lv.setOnItemClickListener(this);
    }

    private void refreshList() {
        Cursor list = elementDataSource.listForCategory(categoryId, sumView);
        Cursor old = adapter.swapCursor(list);
        if (old != null) old.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gegenstand_list_add:
                Fragment fragment = new ElementCreateFragment();
                Bundle args = new Bundle();
                args.putLong("categoryId", categoryId);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack("element")
                        .commit();
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.element_single_list:
                sumView = false;
                getActivity().invalidateOptionsMenu();
                refreshList();
                return true;
            case R.id.element_sum_list:
                sumView = true;
                getActivity().invalidateOptionsMenu();
                refreshList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        elementDataSource.close();
        categoryDataSource.close();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.element_sum_list).setVisible(!sumView);
        menu.findItem(R.id.element_single_list).setVisible(sumView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.element_list_menu, menu);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        if (sumView) return;

        Fragment fragment = new ElementEditFragment();
        Bundle args = new Bundle();
        args.putLong("elementId", id);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack("element")
                .commit();
        getActivity().invalidateOptionsMenu();
    }
}
