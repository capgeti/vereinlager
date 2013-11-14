package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Author: capgeti
 * Date:   13.11.13 22:02
 */
public class PersonDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public PersonDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create(String name, long stimmgruppeId) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("stimmgruppe_id", stimmgruppeId);
        long insertId = database.insert("person", null, values);
    }

    public void delete(long id) {
        database.delete("person", "id = " + id, null);
    }

    public Cursor list(long stimmgruppeId) {
        return database.rawQuery("" +
                "select " +
                "   p.id as _id, " +
                "   p.name " +
                "from person p left join stimmgruppe s on p.stimmgruppe_id = s.id " +
                "where p.stimmgruppe_id = ?",
                new String[]{String.valueOf(stimmgruppeId)});
    }
}
