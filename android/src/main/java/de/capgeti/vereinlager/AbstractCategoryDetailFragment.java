package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.capgeti.vereinlager.db.CategoryDataSource;
import de.capgeti.vereinlager.model.CategoryDetail;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public abstract class AbstractCategoryDetailFragment extends Fragment {
    private List<CategoryDetail> details;
    private CustomArrayAdapter<CategoryDetail> adapter;
    protected CategoryDataSource categoryDataSource;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kategorie_detail, container, false);
        Button saveButton = (Button) view.findViewById(R.id.category_detail_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                onCategorySave();
            }
        });
        Button cancelButton = (Button) view.findViewById(R.id.category_detail_cancel_button);
        cancelButton.setOnClickListener(new
                                                View.OnClickListener() {
                                                    @Override public void onClick(View view) {
                                                        getFragmentManager().popBackStack();
                                                    }
                                                });
        Button addDetailButton = (Button) view.findViewById(R.id.category_detail_add_detail);
        addDetailButton.setOnClickListener(new
                                                   View.OnClickListener() {
                                                       @Override public void onClick(View view) {
                                                           onAddCategoryDetail();
                                                       }
                                                   });
        return view;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        final ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getTitleName());
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

        adapter = new CustomArrayAdapter<CategoryDetail>(activity, R.layout.deleteable_list_item, details) {

            @Override protected void fillView(View listItemView, final int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                CategoryDetail detail = getItem(position);
                final String std = detail.getDefaultValue();
                lineOneView.setText(detail.getName() + (std != null ? " (" + std + ")" : ""));

                ImageButton deleteButton = (ImageButton) listItemView.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        details.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        ListView list = (ListView) activity.findViewById(R.id.kategorie_detail_detail_list);
        list.setAdapter(adapter);
    }

    public abstract List<CategoryDetail> loadList();

    public void onAddCategoryDetail() {
        EditText text = (EditText) getActivity().findViewById(R.id.kategorie_detail_detail_text);
        EditText std = (EditText) getActivity().findViewById(R.id.kategorie_detail_std_text);
        final String s = std.getText().toString();
        details.add(new CategoryDetail(text.getText().toString(), !s.isEmpty() ? s : null));
        text.setText("");
        std.setText("");
        text.requestFocus();
        adapter.notifyDataSetChanged();
    }

    public void onCategorySave() {
        Activity activity = getActivity();
        EditText nameText = (EditText) activity.findViewById(R.id.kategorie_detail_name);
        EditText itemNameText = (EditText) activity.findViewById(R.id.kategorie_detail_gegenstand_std_name);

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

    protected abstract void saveCategory(String name, String itemName, List<CategoryDetail> details);

}