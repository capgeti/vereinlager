package de.capgeti.vereinlager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * @author mwolter
 * @since 28.08.13 15:07
 */
public class Settings extends Activity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DetailFragment detailFragment = new DetailFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, detailFragment)
                .commit();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(detailFragment);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class DetailFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            Preference websiteButton = findPreference("websiteButton");
            websiteButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override public boolean onPreferenceClick(Preference preference) {
                    Uri website = Uri.parse("http://www.capgeti.de");
                    startActivity(new Intent(Intent.ACTION_VIEW, website));
                    return true;
                }
            });
            Preference moreInfos = findPreference("moreInfos");
            moreInfos.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override public boolean onPreferenceClick(Preference preference) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Vereinslagerverwaltung")
                            .setView(getActivity()
                                    .getLayoutInflater()
                                    .inflate(R.layout.info, null))
                            .setNeutralButton("Ok", null)
                            .create().show();
                    return true;
                }
            });
            Preference spendenButton = findPreference("spendenButton");
            spendenButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Uri paypal = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ECS6ZPNNLEXNE");
                    startActivity(new Intent(Intent.ACTION_VIEW, paypal));
                    return true;
                }
            });


            updatePrefs(getPreferenceManager().getSharedPreferences());
        }

        private void updatePrefs(SharedPreferences sharedPreferences) {
            if (sharedPreferences.contains("vereinname")) {
                findPreference("vereinname").setSummary(sharedPreferences.getString("vereinname", "Unbenannt"));
            }
        }

        @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            updatePrefs(sharedPreferences);
        }
    }
}
