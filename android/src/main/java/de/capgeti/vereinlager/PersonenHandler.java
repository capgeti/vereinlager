package de.capgeti.vereinlager;

import android.content.Context;
import de.capgeti.vereinlager.model.Person;
import org.mapdb.*;

import java.util.*;

/**
 * Author: capgeti
 * Date:   06.10.13 00:47
 */
public class PersonenHandler extends AbstractDBHandler {
    private static PersonenHandler handler;

    public static PersonenHandler get(Context context) {
        if (handler == null) handler = new PersonenHandler();
        handler.setContext(context);
        return handler;
    }

    public static Person convertPerson(long id, Map<String, Object> data) {
        if (data == null) return null;
        return new Person(id, (String) data.get("name"));
    }

    public static List<Person> convertList(BTreeMap<Long, Map<String, Object>> personen) {
        List<Person> personenList = new ArrayList<Person>();
        for (Map.Entry<Long, Map<String, Object>> entry : personen.entrySet()) {
            personenList.add(convertPerson(entry.getKey(), entry.getValue()));
        }
        return personenList;
    }

    public Person createPerson(final String name, final Long stimmgruppeId) {
        return transaction(getTx(), new Action<Person>() {
            @Override public Person doIt(DB db) {
                final Atomic.Long personenKey = db.getAtomicLong("personen_key");
                final Long id = personenKey.get();
                personenKey.set(id + 1);
                Person person = new Person(id, name);

                final NavigableSet<Fun.Tuple2<Long, Long>> stimmgruppen_personen = db.getTreeSet("stimmgruppen_personen");
                stimmgruppen_personen.add(Fun.t2(stimmgruppeId, id));

                final BTreeMap<Long, Map<String, Object>> personenMap = db.getTreeMap("personen");

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", person.getName());
                personenMap.put(id, map);

                return person;
            }
        });
    }

    public List<Person> listPersonen(final Long stimmgruppeId) {
        return transaction(getTx(), new Action<List<Person>>() {
            @Override public List<Person> doIt(DB db) {
                final NavigableSet<Fun.Tuple2<Long, Long>> stimmgruppen_personen = db.getTreeSet("stimmgruppen_personen");
                List<Person> persons = new ArrayList<Person>();
                for (Long personId : Bind.findVals2(stimmgruppen_personen, stimmgruppeId)) {
                    persons.add(loadPerson(personId));
                }
                return persons;
            }
        });
    }

    public Person editPerson(final Long id, final String name) {
        return transaction(getTx(), new Action<Person>() {
            @Override public Person doIt(DB db) {
                final BTreeMap<Long, Map<String, Object>> personenMap = db.getTreeMap("personen");
                final Map<String, Object> person = personenMap.get(id);
                person.put("name", name);
                personenMap.put(id, person);
                return convertPerson(id, person);
            }
        });
    }

    public Person loadPerson(final Long id) {
        return transaction(getTx(), new Action<Person>() {
            @Override public Person doIt(DB db) {
                final BTreeMap<Long, Map<String, Object>> personenMap = db.getTreeMap("personen");
                return convertPerson(id, personenMap.get(id));
            }
        });
    }

    public void deletePerson(final Long id) {
        transaction(getTx(), new Action<Void>() {
            @Override public Void doIt(DB db) {
                final BTreeMap<Long, Map<String, Object>> personen = db.getTreeMap("personen");
                personen.remove(id);
                return null;
            }
        });
    }
}
