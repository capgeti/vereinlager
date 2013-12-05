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

    public long create(long categoryId, String name, List<Detail> details) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category_id", categoryId);
        values.put("details", gson().toJson(details));
        return database.insert("element", null, values);
    }

    public Cursor detail(long id) {
        return database.rawQuery("SELECT e.*, p.id as personId FROM element e LEFT JOIN person p ON e.person_id = p.id WHERE e.id = ?", new String[]{valueOf(id)});
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

    public Cursor list(long categoryId, boolean sumView) {
        if (sumView) {
            return database.rawQuery("SELECT name, details, id as _id, count(details) as groupCount, " +
                    "   count(person_id) as used " +
                    "FROM element " +
                    "WHERE category_id = ? " +
                    "GROUP BY details " +
                    "ORDER BY name",
                    new String[]{valueOf(categoryId)});
        } else {
            return database.rawQuery("SELECT e.*, e.id as _id, p.name as person FROM element e LEFT JOIN person p ON e.person_id = p.id WHERE e.category_id = ? ORDER BY name",
                    new String[]{valueOf(categoryId)});
        }
    }

    public void assignPerson(long elementId, Long personId) {
        ContentValues values = new ContentValues();
        values.put("person_id", personId);
        database.update("element", values, "id = ?", new String[]{valueOf(elementId)});
    }
}
