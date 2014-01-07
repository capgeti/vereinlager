package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.google.gson.reflect.TypeToken;
import de.capgeti.vereinlager.db.ElementDataSource;
import de.capgeti.vereinlager.db.PersonDataSource;
import de.capgeti.vereinlager.model.Detail;
import de.capgeti.vereinlager.util.CustomCursorAdapter;
import de.capgeti.vereinlager.util.GsonHelper;

import java.util.List;

import static android.view.View.GONE;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Author: capgeti
 * Date:   14.09.13 03:16
 */
public abstract class AbstractPersonDetailFragment extends Fragment {
    protected ElementDataSource elementDataSource;
    protected PersonDataSource personDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.person_detail, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onElementSave();
                break;
            case R.id.action_cancel:
                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract String getTitleName();

    @Override
    public void onPause() {
        super.onPause();
        elementDataSource.close();
        personDataSource.close();
    }

    @Override
    public void onResume() {
        super.onPause();

        Activity activity = getActivity();
        final ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getTitleName());
        actionBar.setIcon(R.drawable.ic_action_person_white);
        setHasOptionsMenu(true);

        elementDataSource.open();
        personDataSource.open();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        elementDataSource = new ElementDataSource(activity);
        personDataSource = new PersonDataSource(activity);

        setUp();

        final ListView list = (ListView) activity.findViewById(R.id.person_element_list);
        Cursor listCursor = loadList();

        if(listCursor != null) {
            list.setAdapter(new CustomCursorAdapter(activity, R.layout.element_list_item, listCursor) {
                @Override
                protected void fillView(View listItemView, Cursor position) {
                    TextView name = (TextView) listItemView.findViewById(R.id.elementName);
                    TextView besitzer = (TextView) listItemView.findViewById(R.id.elementOwner);
                    besitzer.setVisibility(GONE);
                    TextView params = (TextView) listItemView.findViewById(R.id.elementValues);

                    name.setText(position.getString(position.getColumnIndex("name")));
                    name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_labels, 0, 0, 0);

                    String details = position.getString(position.getColumnIndex("details"));
                    List<Detail> detailList = GsonHelper.gson().fromJson(details, new TypeToken<List<Detail>>() {
                    }.getType());

                    String paramString = "";
                    for (Detail detail : detailList) {
                        paramString += detail.getName() + ": " + detail.getValue() + " ";
                    }
                    params.setText(paramString);
                }
            });
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Fragment fragment = new ElementEditFragment();
                    Bundle args = new Bundle();
                    args.putLong("elementId", id);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack("element")
                            .commit();
                    getActivity().invalidateOptionsMenu();
                }
            });
        }
    }

    protected abstract void setUp();

    public abstract Cursor loadList();

    public void onElementSave() {
        Activity activity = getActivity();
        EditText nameText = (EditText) activity.findViewById(R.id.name);
        EditText firstNameText = (EditText) activity.findViewById(R.id.firstName);
        EditText nickNameText = (EditText) activity.findViewById(R.id.nickName);

        String name = nameText.getText().toString();
        String firstName = firstNameText.getText().toString();
        String nickName = nickNameText.getText().toString();

        if (name.isEmpty() && firstName.isEmpty() && nickName.isEmpty()) {
            Toast.makeText(activity, "Mindestens ein Feld ausfüllen!", LENGTH_SHORT).show();
            return;
        }

        savePerson(name, firstName, nickName);
        Toast.makeText(getActivity(), "Gespeichert...", 2).show();
        getFragmentManager().popBackStack();
    }

    protected abstract void savePerson(String name, String firstName, String nickName);

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.simple_edit_menu, menu);
    }
}