package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Lager extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lager);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Lager von " + PreferenceManager.getDefaultSharedPreferences(this).getString("vereinname", "Unbenannt"));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_gruppe:
                showAddGruppeDialog();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddGruppeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Neue Gruppe hinzufügen");

        final EditText addgruppe = new EditText(this);
        addgruppe.setHint("Gruppenname");

        builder.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Lager.this, addgruppe.getText(), 2).show();
            }
        });

        builder.setNegativeButton("Abbrechen", null);

        builder.setView(addgruppe);
        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(addgruppe, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        alertDialog.show();
    }
}
