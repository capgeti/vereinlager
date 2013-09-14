package de.capgeti.vereinlager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Author: capgeti
 * Date:   05.09.13 22:43
 */
public class Main extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.firstFragment, new MainList());
        transaction.add(R.id.secondFragment, new KategorieList());
        transaction.commit();

        setContentView(R.layout.main);
    }
}