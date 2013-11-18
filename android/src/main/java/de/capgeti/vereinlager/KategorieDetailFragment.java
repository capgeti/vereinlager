package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.capgeti.vereinlager.model.KategorieDetail;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public class KategorieDetailFragment extends Fragment {
    private List<KategorieDetail> details = new ArrayList<KategorieDetail>();
    private CustomArrayAdapter<KategorieDetail> adapter;
    private boolean create;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kategorie_detail, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        Activity activity = getActivity();
        Bundle arguments = getArguments();
        if (!arguments.containsKey("create")) {
            Toast.makeText(activity, "Fehler! lol?", 2).show();
            getFragmentManager().popBackStack();
        }

        create = arguments.getBoolean("create", false);
        if (create) {
            actionBar.setTitle("Kategorie anlegen");
        } else {
            final String name = arguments.getString("kategorie");
            actionBar.setTitle("'" + name + "' bearbeiten");
        }


        adapter = new CustomArrayAdapter<KategorieDetail>(activity, R.layout.deleteable_list_item, details) {

            @Override protected void fillView(View listItemView, final int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                KategorieDetail detail = getItem(position);
                final String std = detail.getStandard();
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

    public void addDetail(View view) {
        EditText text = (EditText) getActivity().findViewById(R.id.kategorie_detail_detail_text);
        EditText std = (EditText) getActivity().findViewById(R.id.kategorie_detail_std_text);
        final String s = std.getText().toString();
        details.add(new KategorieDetail(text.getText().toString(), !s.isEmpty() ? s : null));
        text.setText("");
        std.setText("");
        text.requestFocus();
        adapter.notifyDataSetChanged();
    }

    public void save(View view) {
        Toast.makeText(getActivity(), "Gespeichert...", 2).show();
        getFragmentManager().popBackStack();
    }
}