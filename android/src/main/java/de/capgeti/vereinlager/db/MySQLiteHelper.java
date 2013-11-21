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
        db.execSQL("create table person (id integer primary key autoincrement, member_id long references member(id), name text)");
        db.execSQL("create table member (id integer primary key autoincrement, name text)");

        db.execSQL("create table category (id integer primary key autoincrement, name text, itemName text)");
        db.execSQL("create table detailtemplate (id integer primary key autoincrement, name text, defaultValue text, category_id long references category(id))");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS member");
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS detailtemplate");

        onCreate(db);
    }
}
