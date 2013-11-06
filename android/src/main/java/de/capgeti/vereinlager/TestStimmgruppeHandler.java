package de.capgeti.vereinlager;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * Author: capgeti
 * Date:   06.10.13 00:47
 */
public class TestStimmgruppeHandler extends StimmgruppeHandler {
    @Override protected DB createDB() {
        return DBMaker.newMemoryDB()
                .closeOnJvmShutdown()
                .make();
    }
}
