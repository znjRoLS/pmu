package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;

import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/20/17.
 */

public class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        Preference myPref = (Preference) findPreference("myKey");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                //open browser or intent here
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();

                return true;
            }
        });

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {

        Log.e("nesto", "Nnesto");

        preference.setSummary(o.toString());

        return false;
    }
}
