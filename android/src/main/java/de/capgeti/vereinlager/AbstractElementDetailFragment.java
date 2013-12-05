package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import de.capgeti.vereinlager.db.ElementDataSource;
import de.capgeti.vereinlager.db.PersonDataSource;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public abstract class AbstractElementDetailFragment extends Fragment {
    protected ElementDataSource elementDataSource;
    protected PersonDataSource personDataSource;
    private List<Detail> details;
    private DetailAdapter adapter;
    private Long personId;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.element_detail, container, false);

        Button addDetailButton = (Button) view.findViewById(R.id.element_detail_add_detail);
        addDetailButton.setOnClickListener(new

                                                   View.OnClickListener() {
                                                       @Override public void onClick(View view) {
                                                           details.add(new Detail(null, null));
                                                           adapter.notifyDataSetChanged();
                                                       }
                                                   });

        return view;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onElementSave();
                break;
            case R.id.action_cancel:
                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        personDataSource.open();
        if (requestCode == 1 && resultCode == RESULT_OK) {
            long tmp = data.getLongExtra("personId", -1);
            Long personId = tmp == -1 ? null : tmp;
            updatePersonHandling(personId);
        }
    }


    protected void updatePersonHandling(final Long personId) {
        this.personId = personId;
        final TextView peronNameView = (TextView) getActivity().findViewById(R.id.element_detail_person_name);

        final ImageButton assignPerson = (ImageButton) getActivity().findViewById(R.id.element_detail_assign_person);
        assignPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), PersonAssignActivity.class), 1);
            }
        });

        final ImageButton deleteButton = (ImageButton) getActivity().findViewById(R.id.element_detail_delete_person);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementDataSource.assignPerson(personId, null);
                Toast.makeText(getActivity(), "Person getrennt", Toast.LENGTH_SHORT).show();
                updatePersonHandling(null);
            }
        });


        deleteButton.setVisibility(personId != null ? VISIBLE : GONE);
        assignPerson.setVisibility(personId == null ? VISIBLE : GONE);
        if(personId != null) {
            try (Cursor c = personDataSource.detail(personId)) {
                if (c.moveToFirst()) {
                    peronNameView.setText(c.getString(c.getColumnIndex("name")));
                }
            }
        } else {
            peronNameView.setText("Keiner");
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        final ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getTitleName());
        actionBar.setIcon(R.drawable.ic_action_labels_white);
        setHasOptionsMenu(true);
    }

    public abstract String getTitleName();

    @Override public void onPause() {
        super.onPause();
        elementDataSource.close();
        personDataSource.close();
    }
    @Override public void onResume() {
        super.onPause();
        elementDataSource.open();
        personDataSource.open();
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        elementDataSource = new ElementDataSource(activity);
        personDataSource = new PersonDataSource(activity);

        updatePersonHandling(null);

        setUp();

        details = loadList();
        adapter = new DetailAdapter(activity.getLayoutInflater(), details);
        ListView list = (ListView) activity.findViewById(R.id.element_detail_detail_list);
        list.setAdapter(adapter);
    }

    protected abstract void setUp();

    public abstract List<Detail> loadList();

    public void onElementSave() {
        Activity activity = getActivity();
        EditText nameText = (EditText) activity.findViewById(R.id.element_detail_name);

        String name = nameText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(activity, "Bitte einen Namen angeben!", LENGTH_SHORT).show();
            return;
        }

        Long elementId = saveElement(name, details);
        elementDataSource.assignPerson(elementId, personId);
        Toast.makeText(getActivity(), "Gespeichert...", 2).show();
        getFragmentManager().popBackStack();
    }

    protected abstract Long saveElement(String name, List<Detail> details);

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.simple_edit_menu, menu);
    }
}