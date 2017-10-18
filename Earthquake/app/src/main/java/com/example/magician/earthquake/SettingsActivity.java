package com.example.magician.earthquake;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


public class SettingsActivity extends AppCompatActivity {
    public static final String LOG_TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.v(LOG_TAG, "onCreate SettingsActivity");
        //noinspection ConstantConditions
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //PreferenceFragment can implement the OnPreferenceChangeListener
    // interface to get notified when a preference changes.
    public static class EarthquakePreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            //PreferenceFragment's findPreference() method to get the Preference object
            //used to setup the preference
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference maxMagnitude = findPreference(getString(R.string.settings_max_magnitude_key));
            bindPreferenceSummaryToValue(maxMagnitude);//error here

            Preference itemNumber = findPreference(getString(R.string.settings_item_number_key));
            bindPreferenceSummaryToValue(itemNumber);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
            Log.v(LOG_TAG, "onCreate EarthquakePreferenceFragment");
        }

        //Then when a single Preference has been changed by the user and is about to be saved,
        // the onPreferenceChange() method will be invoked with the key of the preference that was changed.
        //or prevent a proposed preference change by returning false.
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            // check if it was ListPreference or String
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }
            else if(preference instanceof EditTextPreference)
            {
                if(preference.getKey().equals(getString(R.string.settings_item_number_key)))
                {
                    if (Integer.parseInt(stringValue) > 0 && Integer.parseInt(stringValue) <= 100)
                        preference.setSummary(stringValue);
                    else if (Integer.parseInt((stringValue))<=0)
                        preference.setSummary("1");
                    else
                        preference.setSummary("100");
                }
                else
                    preference.setSummary(stringValue);
            }
            return true;
        }

        //set the current EarthquakePreferenceFragment instance as the listener on each preference.
        //also read the current value of the preference stored in the SharedPreferences on the device
        //, and display that in the preference summary
        // (so that the user can see the current value of the preference).
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);//error here
            SharedPreferences preferences = PreferenceManager.
                    getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }

}
