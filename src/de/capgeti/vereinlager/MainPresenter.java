package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Voice;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.TxMaker;

import java.io.File;

/**
 * Author: capgeti
 * Date:   29.08.13 22:23
 */
public class MainPresenter implements MainDisplay {
    private final TxMaker txMaker;
    private final MainView mainView;

    public MainPresenter() {
        this.txMaker = DBMaker.newFileDB(new File("data.txMaker")).closeOnJvmShutdown().makeTxMaker();
        mainView = new MainView(this);
        mainView.showWindow();
    }

    @Override public Voice onCreateVoice(String voiceName) {
        final Voice key = new Voice(voiceName);
        final DB tx = txMaker.makeTx();
        tx.getTreeMap("voices").put(voiceName, key);
        tx.commit();
        return key;
    }

    @Override public Person onCreatePerson(String voiceName, String name) {
        final DB tx = txMaker.makeTx();
        final BTreeMap<Object, Object> voices = tx.getTreeMap("voices");
        if (voices.containsKey(voiceName)) {
            Voice voice = (Voice) voices.get(voiceName);
            final Person person = new Person(name);
            voice.addPerson(person);
            tx.commit();
            return person;
        }
        tx.close();
        return null;
    }
}
