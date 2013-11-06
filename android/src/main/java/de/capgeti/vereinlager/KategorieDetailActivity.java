package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;
import android.widget.*;
import de.capgeti.vereinlager.model.KategorieDetail;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public class KategorieDetailActivity extends Activity {
    private List<KategorieDetail> details = new ArrayList<KategorieDetail>();
    private CustomArrayAdapter<KategorieDetail> adapter;
    private boolean create;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorie_detail);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (!getIntent().hasExtra("create")) {
            Toast.makeText(this, "Fehler! lol?", 2).show();
            navigateUpTo(new Intent(this, KategorieListActivity.class));
        }

        create = getIntent().getBooleanExtra("create", false);
        if (create) {
            actionBar.setTitle("Kategorie anlegen");
        } else {
            final String name = getIntent().getStringExtra("kategorie");
            actionBar.setTitle("'" + name + "' bearbeiten");
        }


        adapter = new CustomArrayAdapter<KategorieDetail>(this, R.layout.deleteable_list_item, details) {

            @Override protected void fillView(View listItemView, final int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                KategorieDetail detail = getItem(position);
                final String std = detail.getStandard();
                lineOneView.setText(detail.getName() + (std != null ? " (" + std + ")" : ""));

                Button deleteButton = (Button) listItemView.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        details.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        ListView list = (ListView) findViewById(R.id.kategorie_detail_detail_list);
        list.setAdapter(adapter);
    }

    public void addDetail(View view) {
        EditText text = (EditText) findViewById(R.id.kategorie_detail_detail_text);
        EditText std = (EditText) findViewById(R.id.kategorie_detail_std_text);
        final String s = std.getText().toString();
        details.add(new KategorieDetail(text.getText().toString(), !s.isEmpty() ? s : null));
        text.setText("");
        std.setText("");
        text.requestFocus();
        adapter.notifyDataSetChanged();
    }

    public void save(View view) {
        Toast.makeText(this, "Gespeichert...", 2).show();
        navigateUpTo(new Intent(this, KategorieListActivity.class));
    }
}