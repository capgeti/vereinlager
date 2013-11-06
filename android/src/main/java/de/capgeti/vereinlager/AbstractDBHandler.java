package de.capgeti.vereinlager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;


/**
 * Author: capgeti
 * Date:   03.10.13 16:58
 */
public class AbstractDBHandler {
    private DB txMaker;
    protected Context context;

    protected <D> D transaction(DB txMaker, Action<D> action) {
        final DB db = txMaker;
        try {
            D object = action.doIt(db);
            db.commit();
            return object;
        } catch (Exception e) {
            db.rollback();
            Toast.makeText(this.context, "Fehler: " + e.getMessage(), 2).show();
            Log.e("KABUFF", "Stimmgruppe anlegen", e);
        } finally {
//            db.close();
        }
        return null;
    }

    protected DB createDB() {
        return DBMaker.newFileDB(new File(Environment.getExternalStorageDirectory(), "testDb"))
                .randomAccessFileEnable()
                .asyncWriteDisable()
                .closeOnJvmShutdown()
                .make();
    }

    protected DB getTx() {
        if (txMaker == null) txMaker = createDB();
        return txMaker;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected interface Action<T> {
        T doIt(DB db);
    }
}
