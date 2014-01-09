package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static java.lang.String.*;
import static java.lang.String.valueOf;

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

    public void create(String name, String firstName, String nickName, long memberId) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("firstName", firstName);
        values.put("nickName", nickName);
        values.put("member_id", memberId);
        database.insert("person", null, values);
    }

    public void update(long id, String name, String firstName, String nickName) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("firstName", firstName);
        values.put("nickName", nickName);
        database.update("person", values, "id = ?", new String[]{valueOf(id)});
    }

    public void delete(long id) {
        database.delete("person", "id = ?", new String[]{valueOf(id)});
        ContentValues contentValues = new ContentValues();
        contentValues.put("person_id", (Long) null);
        database.update("element", contentValues, "person_id = ?", new String[]{valueOf(id)});
    }

    public Cursor list(Long memberId) {
        if(memberId == null) {
            return database.rawQuery("SELECT *, id AS _id FROM person ORDER BY name", new String[0]);
        }
        return database.rawQuery("SELECT p.*, p.id as _id FROM person p LEFT JOIN member s ON p.member_id = s.id " +
                "WHERE p.member_id = ? ORDER BY p.name", new String[]{valueOf(memberId)});
    }

    public Cursor detail(Long personId) {
        return database.rawQuery("SELECT * FROM person WHERE id = ?", new String[]{valueOf(personId)});
    }

    public void setMember(long personId, long memberId) {
        ContentValues values = new ContentValues();
        values.put("member_id", memberId);
        database.update("person", values, "id = ?", new String[]{valueOf(personId)});
    }
}
