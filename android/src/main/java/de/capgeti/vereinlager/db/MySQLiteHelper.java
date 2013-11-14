package de.capgeti.vereinlager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: capgeti
 * Date:   13.11.13 21:57
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public MySQLiteHelper(Context context) {
        super(context, "kabuff", null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table person (id integer primary key autoincrement, stimmgruppe_id long references stimmgruppe(id), name text)");
        db.execSQL("create table stimmgruppe (id integer primary key autoincrement, name text)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS stimmgruppe");
        db.execSQL("DROP TABLE IF EXISTS person");

        onCreate(db);
    }
}
