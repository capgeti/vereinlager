package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Voice;

import java.util.Map;

/**
 * Author: capgeti
 * Date:   29.08.13 22:28
 */
public interface MainDisplay {
    Voice onCreateVoice(String voiceName);

    Person onCreatePerson(String voiceName, String name);

    Map<String, Voice> getVoices();

    Voice onRenameVoice(Voice voice, String newName);
}
