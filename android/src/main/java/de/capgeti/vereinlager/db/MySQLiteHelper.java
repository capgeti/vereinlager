package de.capgeti.vereinlager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: capgeti
 * Date:   13.11.13 21:57
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;

    public MySQLiteHelper(Context context) {
        super(context, "kabuff", null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table member (id integer primary key autoincrement, name text)");
        db.execSQL("create table person (id integer primary key autoincrement, member_id long references member(id), name text, firstName text, nickName text)");

        db.execSQL("create table category (id integer primary key autoincrement, name text, details text)");
        db.execSQL("create table element (id integer primary key autoincrement, name text, details text, category_id long references category(id), person_id long references person(id))");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS member");
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS element");

        onCreate(db);
    }
}
