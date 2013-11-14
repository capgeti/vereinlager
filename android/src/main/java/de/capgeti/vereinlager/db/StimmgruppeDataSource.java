package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Author: capgeti
 * Date:   13.11.13 22:02
 */
public class StimmgruppeDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public StimmgruppeDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        long insertId = database.insert("stimmgruppe", null, values);
    }

    public Cursor detail(long id) {
        return database.rawQuery("select id as _id, name from stimmgruppe where id = ?",
                new String[]{String.valueOf(id)});
    }

    public void update(long id, String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        database.update("stimmgruppe", values, "id = " + id, null);
    }

    public void delete(long id) {
        database.delete("stimmgruppe", "id = " + id, null);
    }

    public Cursor list() {
        return database.rawQuery("" +
                "select s.id as _id, " +
                "       s.name, " +
                "       (" +
                "           select " +
                "               count( p.id )" +
                "           from person p " +
                "           where p.stimmgruppe_id = s.id" +
                "       ) " +
                "from stimmgruppe s " +
                "group by s.name " +
                "order by s.name;", new String[0]);
    }

}
