package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static java.lang.String.valueOf;

/**
 * Author: capgeti
 * Date:   13.11.13 22:02
 */
public class MemberDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private PersonDataSource personDataSource;

    public MemberDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        personDataSource = new PersonDataSource(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
        personDataSource.open();
    }

    public void close() {
        dbHelper.close();
        personDataSource.close();
    }

    public void create(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        long insertId = database.insert("member", null, values);
    }

    public Cursor detail(long id) {
        return database.rawQuery("select id as _id, name from member where id = ?",
                new String[]{valueOf(id)});
    }

    public void update(long id, String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        database.update("member", values, "id = ?", new String[]{valueOf(id)});
    }

    public void delete(long id) {
        try(Cursor list = personDataSource.list(id)) {
            while(list.moveToNext()) {
                personDataSource.delete(list.getLong(list.getColumnIndex("_id")));
            }
        }
        database.delete("member", "id = ?", new String[]{valueOf(id)});
    }

    public Cursor list() {
        return database.rawQuery("" +
                "select s.id as _id, " +
                "       s.name, " +
                "       (" +
                "           select " +
                "               count( p.id )" +
                "           from person p " +
                "           where p.member_id = s.id" +
                "       ) " +
                "from member s " +
                "group by s.name " +
                "order by s.name;", new String[0]);
    }

}
