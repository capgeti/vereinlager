package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.capgeti.vereinlager.model.Kategorie;
import de.capgeti.vereinlager.util.CustomArrayAdapter;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Author: capgeti
 * Date:   05.09.13 23:11
 */
public class KategorieListFragment extends ListFragment {
    List<Kategorie> countries = asList(new Kategorie(0, "Lefima Alt", 4, 10));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        CustomArrayAdapter<Kategorie> adapter = new CustomArrayAdapter<Kategorie>(getActivity(), R.layout.double_text_list, countries) {

            @Override protected void fillView(View listItemView, int position) {
                TextView lineOneView = (TextView) listItemView.findViewById(R.id.text1);
                TextView lineTwoView = (TextView) listItemView.findViewById(R.id.text2);

                Kategorie s = getItem(position);
                lineOneView.setText(s.getName());
                lineOneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_storage, 0, 0, 0);

                lineTwoView.setText(s.getGesamt() + " gesamt (" + s.getFrei() + " frei / " + s.getVerwendet() + " verwendet )");
            }
        };

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Kategorien");

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        final Intent intent = new Intent(getActivity(), GegenstandListActivity.class);
        intent.putExtra("kategorie", countries.get(position).getName());
        startActivity(intent);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kategorie_list_add:
                Fragment fragment = new KategorieDetailFragment();
                Bundle args = new Bundle();
                args.putBoolean("create", true);
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack("kategorie")
                        .commit();
                getActivity().invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.kategorie_list_menu, menu);
    }
}
