package de.capgeti.vereinlager;

import android.content.Context;
import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Stimmgruppe;
import org.mapdb.*;

import java.util.*;

/**
 * Author: capgeti
 * Date:   06.10.13 00:47
 */
public class StimmgruppeHandler extends AbstractDBHandler {
    private static StimmgruppeHandler handler;

    public static StimmgruppeHandler get(Context context) {
        if (handler == null) handler = new StimmgruppeHandler();
        handler.setContext(context);
        return handler;
    }

    public static Stimmgruppe convertStimmgruppe(long id, Map<String, Object> data) {
        if(data == null) return null;
        return new Stimmgruppe(id, (String) data.get("name"), (Integer) data.get("personen"));
    }

    public static List<Stimmgruppe> convertList(BTreeMap<Long, Map<String, Object>> stimmgruppe) {
        List<Stimmgruppe> stimmgruppes = new ArrayList<Stimmgruppe>();
        for (Map.Entry<Long, Map<String, Object>> entry : stimmgruppe.entrySet()) {
            stimmgruppes.add(convertStimmgruppe(entry.getKey(), entry.getValue()));
        }
        return stimmgruppes;
    }

    public Stimmgruppe createStimmgruppe(final String name) {
        return transaction(getTx(), new Action<Stimmgruppe>() {
            @Override public Stimmgruppe doIt(DB db) {
                final Atomic.Long stimmgruppe_key = db.getAtomicLong("stimmgruppe_key");
                final Long id = stimmgruppe_key.get();
                stimmgruppe_key.set(id + 1);
                Stimmgruppe stimmgruppe = new Stimmgruppe(id, name, 0);
                final BTreeMap<Long, Map<String, Object>> stimmgruppe1 = db.getTreeMap("stimmgruppe");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", stimmgruppe.getName());
                map.put("personen", stimmgruppe.getPersonen());
                stimmgruppe1.put(id, map);
                return stimmgruppe;
            }
        });
    }

    public List<Stimmgruppe> listStimmgruppen() {
        return transaction(getTx(), new Action<List<Stimmgruppe>>() {
            @Override public List<Stimmgruppe> doIt(DB db) {
                return convertList(db.<Long, Map<String, Object>>getTreeMap("stimmgruppe"));
            }
        });
    }

    public Stimmgruppe editStimmgruppe(final Long id, final String test2) {
        return transaction(getTx(), new Action<Stimmgruppe>() {
            @Override public Stimmgruppe doIt(DB db) {
                final BTreeMap<Long, Map<String, Object>> stimmgruppe = db.getTreeMap("stimmgruppe");
                final Map<String, Object> current = stimmgruppe.get(id);
                current.put("name", test2);
                stimmgruppe.put(id, current);
                return convertStimmgruppe(id, current);
            }
        });
    }
    public Stimmgruppe loadStimmgruppe(final Long id) {
        return transaction(getTx(), new Action<Stimmgruppe>() {
            @Override public Stimmgruppe doIt(DB db) {
                final BTreeMap<Long, Map<String, Object>> stimmgruppe = db.getTreeMap("stimmgruppe");
                return convertStimmgruppe(id, stimmgruppe.get(id));
            }
        });
    }

    public void deleteStimmgruppe(final Long id) {
        transaction(getTx(), new Action<Void>() {
            @Override public Void doIt(DB db) {
                final BTreeMap<Long, Map<String, Object>> stimmgruppe = db.getTreeMap("stimmgruppe");

                final PersonenHandler personenHandler = PersonenHandler.get(context);
                for (Person person : personenHandler.listPersonen(id)) {
                    personenHandler.deletePerson(person.getId());
                }

                stimmgruppe.remove(id);
                return null;
            }
        });
    }
}
