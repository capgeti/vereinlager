package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.capgeti.vereinlager.db.MySQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static de.capgeti.vereinlager.db.MySQLiteHelper.DB_VERSION;


/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class ExportFragment extends Fragment {

    public static final String BACKUP_PATH = "/sdcard/kabuff_v" + DB_VERSION + ".db";
    private static final String DB_FILEPATH = "/data/data/de.capgeti.vereinlager/databases/kabuff";

    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.export, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Exportieren");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        updateButtons();
    }

    private void deleteBackup() {
        try {
            File file = new File(BACKUP_PATH);
            if (file.exists()) {
                file.delete();
            }
            Toast.makeText(getActivity(), "Gelöscht!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Fehler: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            updateButtons();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateButtons();
    }

    private void updateButtons() {
        Activity activity = getActivity();
        Button backupButton = (Button) activity.findViewById(R.id.backupButton);
        TextView backupMsg = (TextView) activity.findViewById(R.id.backupMsg);
        backupMsg.setText("nach " + BACKUP_PATH);
        backupButton.setEnabled(true);
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackup();
            }
        });

        Button restoreButton = (Button) activity.findViewById(R.id.restoreButton);
        TextView restoreMsg = (TextView) activity.findViewById(R.id.restoreMessage);
        boolean exists = existFile();
        restoreMsg.setText((exists ? "von " : "") + BACKUP_PATH + (!exists ? " nicht gefunden! " : ""));
        restoreButton.setEnabled(exists);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRestore();
            }
        });

        Button deleteBackup = (Button) getActivity().findViewById(R.id.deleteBackup);
        deleteBackup.setEnabled(exists);
        deleteBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBackup();
            }
        });

    }

    private boolean existFile() {
        File dbFile = new File(BACKUP_PATH);
        return dbFile.exists();
    }

    public void startBackup() {
        if (existFile()) {
            new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light))
                    .setTitle("Datei vorhanden!")
                    .setMessage(BACKUP_PATH + " überschreiben?")
                    .setNegativeButton("Nein", null)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            backupDb();
                        }
                    }).create().show();
        } else {
            backupDb();
        }
    }

    public void startRestore() {
        MySQLiteHelper helper = new MySQLiteHelper(getActivity());
        try {
            File newDb = new File(BACKUP_PATH);
            File oldDb = new File(DB_FILEPATH);
            if (newDb.exists()) {
                copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
                helper.getWritableDatabase().close();
            }
            Toast.makeText(getActivity(), "Backup erfolgreich wiederhergestellt!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Fehler: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            helper.close();
        }
    }

    private void backupDb() {
        try {
            File backupTarget = new File(BACKUP_PATH);
            backupTarget.delete();
            backupTarget.createNewFile();

            File currentDb = new File(DB_FILEPATH);
            if (backupTarget.exists()) {
                copyFile(new FileInputStream(currentDb), new FileOutputStream(backupTarget));
            }
            Toast.makeText(getActivity(), "Backup erfolgreich erstellt!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Fehler: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        updateButtons();
    }
}

