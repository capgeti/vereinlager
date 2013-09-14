package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Voice;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.Map;

/**
 * Author: capgeti
 * Date:   29.08.13 22:23
 */
public class MainPresenter implements MainDisplay {
    public static final String VOICES = "voices";
    private final DB db;
    private final MainView mainView;

    public MainPresenter() {
        this.db = DBMaker.newFileDB(new File("data.db")).closeOnJvmShutdown().make();
        mainView = new MainView(this);
        mainView.showWindow();
    }

    @Override public Map<String, Voice> getVoices() {
        final BTreeMap<String, Voice> voices = db.getTreeMap(VOICES);
        voices.printTreeStructure();
        return voices;
    }

    @Override public Voice onRenameVoice(Voice voice, String newName) {
        return null;
    }

    @Override public Voice onCreateVoice(String voiceName) {
        final Voice key = new Voice(voiceName);
        db.getTreeMap(VOICES).put(voiceName, key);
        db.commit();
        return key;
    }

    @Override public Person onCreatePerson(String voiceName, String name) {
        final BTreeMap<Object, Object> voices = db.getTreeMap(VOICES);
        if (voices.containsKey(voiceName)) {
            Voice voice = (Voice) voices.get(voiceName);
            final Person person = new Person(name);
            voice.addPerson(person);
            voices.replace(voiceName, voice);
            db.commit();
            return person;
        }
        return null;
    }
}
