package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.capgeti.vereinlager.model.CategoryDetail;

import java.util.List;

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

    public void create(String name, String itemName, List<CategoryDetail> categoryDetails) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("itemName", itemName);
        long id = database.insert("category", null, values);
        addDetails(categoryDetails, id);
    }

    private void addDetails(List<CategoryDetail> categoryDetails, long categoryId) {
        for (CategoryDetail detail : categoryDetails) {
            ContentValues detailValues = new ContentValues();
            detailValues.put("name", detail.getName());
            detailValues.put("defaultValue", detail.getDefaultValue());
            detailValues.put("category_id", categoryId);
            database.insert("detailtemplate", null, detailValues);
        }
    }

    public Cursor detail(long id) {
        return database.rawQuery("select * from category where id = ?", new String[]{valueOf(id)});
    }

    public void update(long id, String name, String itemName, List<CategoryDetail> categoryDetails) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("itemName", itemName);
        database.update("category", values, "id = ?", new String[]{valueOf(id)});
        deleteDetails(id);
        addDetails(categoryDetails, id);
    }

    private void deleteDetails(long id) {
        database.delete("detailtemplate", "category_id = ?", new String[]{valueOf(id)});
    }

    public void delete(long id) {
        deleteDetails(id);
        database.delete("category", "id = ?", new String[]{valueOf(id)});

    }

    public Cursor list() {
        return database.rawQuery("select *, id as _id from category order by name", new String[0]);
    }

    public Cursor listDetails(long id) {
        return database.rawQuery("select * from detailtemplate where category_id = ?", new String[]{valueOf(id)});
    }
}
