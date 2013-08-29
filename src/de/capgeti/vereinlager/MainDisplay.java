package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Voice;

/**
 * Author: capgeti
 * Date:   29.08.13 22:28
 */
public interface MainDisplay {
    Voice onCreateVoice(String voiceName);

    Person onCreatePerson(String voiceName, String name);
}
