package de.capgeti.vereinlager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import de.capgeti.vereinlager.model.Detail;

import java.util.List;

import static de.capgeti.vereinlager.util.GsonHelper.gson;
import static java.lang.String.valueOf;

/**
 * Author: capgeti
 * Date:   13.11.13 22:02
 */
public class ElementDataSource extends AbstractDataSource {

    public ElementDataSource(Context context) {
        super(context);
    }

    public void create(long categoryId, String name, List<Detail> details) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category_id", categoryId);
        values.put("details", gson().toJson(details));
        long id = database.insert("element", null, values);
    }

    public Cursor detail(long id) {
        return database.rawQuery("select * from element where id = ?", new String[]{valueOf(id)});
    }

    public void update(long id, String name, List<Detail> details) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("details", gson().toJson(details));
        database.update("element", values, "id = ?", new String[]{valueOf(id)});
    }

    public void delete(long id) {
        database.delete("element", "id = ?", new String[]{valueOf(id)});
    }

    public Cursor list(long categoryId) {
        return database.rawQuery("SELECT *, id as _id FROM element WHERE category_id = ? ORDER BY name",
                new String[]{valueOf(categoryId)});
    }
}
