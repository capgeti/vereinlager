package de.capgeti.vereinlager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Author: capgeti
 * Date:   27.09.13 00:23
 */
public class SimplePrompt extends AlertDialog.Builder implements DialogInterface.OnShowListener {

    private EditText input;
    private AlertDialog alertDialog;

    public SimplePrompt(Context context, String title, String message, String ok, String cancel, String defaultValue) {
        super(context);
        setTitle(title);
        setMessage(message);
        input = new EditText(context);
        setView(input);

        if(defaultValue != null) {
            input.setText(defaultValue);
        }

        setPositiveButton(ok, null);
        setNegativeButton(cancel, null);

        alertDialog = create();
        alertDialog.setOnShowListener(this);

        alertDialog.show();
    }

    public SimplePrompt(Context context, String title, String message) {
        this(context, title, message, "OK", "Abbrechen", null);
    }

    public SimplePrompt(Context context, String title, String message, String defaultValue) {
        this(context, title, message, "OK", "Abbrechen", defaultValue);
    }

    public void onCancelClicked() {
        alertDialog.dismiss();
    }

    public boolean onOK(String value) {
        return true;
    }

    @Override public void onShow(DialogInterface dialogInterface) {
        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (onOK(input.getText().toString())) {
                    alertDialog.dismiss();
                }
            }
        });
        Button b2 = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                onCancelClicked();
            }
        });

        ((InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE))
                .showSoftInput(input, 0);
    }

}
