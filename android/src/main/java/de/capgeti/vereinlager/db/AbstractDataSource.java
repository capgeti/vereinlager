package de.capgeti.vereinlager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static java.lang.String.valueOf;

/**
 * Author: capgeti
 * Date:   13.11.13 22:02
 */
public class AbstractDataSource {

    protected SQLiteDatabase database;
    protected MySQLiteHelper dbHelper;

    public AbstractDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
