package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.model.GegenstandDetail;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public class GegenstandDetailActivity extends Activity {
    private List<GegenstandDetail> details = new ArrayList<GegenstandDetail>();
    private CustomArrayAdapter<GegenstandDetail> adapter;
    private boolean create;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gegenstand_detail);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (!getIntent().hasExtra("create")) {
            Toast.makeText(this, "Fehler! lol?", 2).show();
            navigateUpTo(new Intent(this, GegenstandListActivity.class));
        }

        create = getIntent().getBooleanExtra("create", false);
        if (create) {
            actionBar.setTitle("Gegenstand anlegen");
        } else {
            final String name = getIntent().getStringExtra("gegenstand");
            actionBar.setTitle("'" + name + "' bearbeiten");
        }


        adapter = new CustomArrayAdapter<GegenstandDetail>(this, R.layout.gegenstand_detail_list_item, details) {

            @Override protected void fillView(View listItemView, final int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.gegenstand_detail_detail_name);
                GegenstandDetail detail = getItem(position);
                lineOneView.setText(detail.getName());
                EditText text = (EditText) listItemView.findViewById(R.id.gegenstand_detail_detail_value);
                text.setText(detail.getName());
            }
        };
        ListView list = (ListView) findViewById(R.id.gegenstand_detail_detail_list);
        list.setAdapter(adapter);
    }

    public void save(View view) {
        Toast.makeText(this, "Gespeichert...", 2).show();
        navigateUpTo(new Intent(this, GegenstandListActivity.class));
    }
}