package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import de.capgeti.vereinlager.db.CategoryDataSource;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public abstract class AbstractCategoryDetailFragment extends Fragment {
    protected CategoryDataSource categoryDataSource;
    private List<Detail> details;
    private DetailAdapter adapter;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.category_detail, container, false);
        Button addButton = (Button) inflate.findViewById(R.id.category_detail_add_detail);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                onAddCategoryDetail();
            }
        });
        return inflate;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        final ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getTitleName());
        actionBar.setIcon(R.drawable.ic_action_storage_white);

        setHasOptionsMenu(true);
        categoryDataSource = new CategoryDataSource(activity);
    }

    public abstract String getTitleName();

    @Override public void onPause() {
        super.onPause();
        categoryDataSource.close();
    }

    @Override public void onResume() {
        super.onResume();
        categoryDataSource.open();
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        details = loadList();
        adapter = new DetailAdapter(activity.getLayoutInflater(), details);
        ListView list = (ListView) activity.findViewById(R.id.category_detail_detail_list);
        list.setAdapter(adapter);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onCategorySave();
                return true;
            case R.id.action_cancel:
                getFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.simple_edit_menu, menu);
    }

    public abstract List<Detail> loadList();

    public void onAddCategoryDetail() {
        details.add(new Detail("", ""));
        adapter.notifyDataSetChanged();
    }

    public void onCategorySave() {
        Activity activity = getActivity();
        EditText nameText = (EditText) activity.findViewById(R.id.category_detail_name);
        EditText itemNameText = (EditText) activity.findViewById(R.id.category_detail_item_name);

        String name = nameText.getText().toString();
        if (name == null || name.isEmpty()) {
            Toast.makeText(activity, "Bitte einen Namen angeben!", LENGTH_SHORT).show();
            return;
        }
        String itemName = itemNameText.getText().toString();
        if (itemName == null || itemName.isEmpty()) {
            Toast.makeText(activity, "Bitte einen Standardnamen für ein Element angeben!", LENGTH_SHORT).show();
            return;
        }

        saveCategory(name, itemName, details);
        Toast.makeText(getActivity(), "Gespeichert...", 2).show();
        getFragmentManager().popBackStack();
    }

    protected abstract void saveCategory(String name, String itemName, List<Detail> details);

}