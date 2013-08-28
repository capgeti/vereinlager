package de.capgeti.vereinlager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@android.widget.RemoteViews.RemoteView
public class GruppenListe extends ListView {

    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

    public GruppenListe(Context context, AttributeSet attrs) {
        super(context, attrs);
        create();
    }

    public GruppenListe(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create();
    }

    public GruppenListe(Context context) {
        super(context);
        create();
    }

    private void create() {
        createRow("Hemden", "40", "12");
        createRow("Röcke", "40", "24");
        createRow("Tshirt-Alt", "10", "4");
        createRow("Tshirt-Neu", "10", "34");
        createRow("MT Steinbach", "2", "5");
        createRow("MT Lefima", "2", "8");
        createRow("MT Chester", "6", "0");
        createRow("Tom", "6", "22");
        createRow("JamBlock", "6", "0");

        String[] fromColumns = {"gruppenName", "anzahl", "vergeben"};
        int[] toViews = {R.id.text01, R.id.text02, R.id.text03};

        adapter = new SimpleAdapter(getContext(), data, R.layout.lager_gruppen_item, fromColumns, toViews);
        setAdapter(adapter);
    }

    private void createRow(String name, String lager, String weg) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("gruppenName", name);
        map.put("anzahl", lager);
        map.put("vergeben", weg);
        data.add(map);
    }


}
