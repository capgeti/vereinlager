package de.capgeti.vereinlager;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import de.capgeti.vereinlager.model.Kategorie;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class KategorieList extends ListFragment {
    Kategorie[] countries = new Kategorie[]{new Kategorie("Lefima Alt", 4, 10)};
    private boolean isSecondFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isSecondFragment = this.equals(getFragmentManager().findFragmentById(R.id.secondFragment));

        CustomArrayAdapter<Kategorie> adapter = new CustomArrayAdapter<Kategorie>(inflater.getContext(), R.layout.kategorie_list, countries) {

            @Override protected void fillView(View listItemView, int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) listItemView.findViewById(R.id.text2);

                Kategorie s = getItem(position);
                lineOneView.setText(s.getName());
                lineTwoView.setText(s.getGesamt() + " (" + s.getFrei() + " / " + s.getVerwendet() + ")");
            }
        };

        setHasOptionsMenu(isSecondFragment);

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (!isSecondFragment) return;

        transaction.replace(R.id.firstFragment, new KategorieList());
        transaction.replace(R.id.secondFragment, new GegenstandList());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.kategorie_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
