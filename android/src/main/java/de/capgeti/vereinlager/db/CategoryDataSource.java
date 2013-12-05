package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static de.capgeti.vereinlager.util.GsonHelper.gson;
import static java.lang.String.valueOf;

/**
 * Author: capgeti
 * Date:   13.11.13 22:02
 */
public class CategoryDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public CategoryDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create(String name, String itemName, List<Detail> details) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("itemName", itemName);
        values.put("details", gson().toJson(details));
        database.insert("category", null, values);
    }

    public Cursor detail(long id) {
        return database.rawQuery("select * from category where id = ?", new String[]{valueOf(id)});
    }

    public void update(long id, String name, String itemName, List<Detail> details) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("itemName", itemName);
        values.put("details", gson().toJson(details));
        database.update("category", values, "id = ?", new String[]{valueOf(id)});
    }

    public void delete(long id) {
        database.delete("element", "category_id = ?", new String[]{valueOf(id)});
        database.delete("category", "id = ?", new String[]{valueOf(id)});
    }

    public Cursor list() {
        return database.rawQuery("" +
                "SELECT c.*, c.id as _id, " +
                "   (SELECT count(*) FROM element where category_id = c.id) as elements, " +
                "   (SELECT count(*) FROM element where category_id = c.id AND person_id is not null) as used " +
                "FROM category c " +
                "ORDER BY name", new String[0]);
    }
}
