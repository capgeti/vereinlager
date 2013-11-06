package de.capgeti.vereinlager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Author: capgeti
 * Date:   05.09.13 22:43
 */
public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void showKategorieList(View view) {
        startActivity(new Intent(this, KategorieListActivity.class));
    }

    public void showStimmgruppenList(View view) {
        startActivity(new Intent(this, StimmgruppenListActivity.class));
    }
}