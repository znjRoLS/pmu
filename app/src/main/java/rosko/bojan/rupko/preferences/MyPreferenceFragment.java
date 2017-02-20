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

public class MyPreferenceFragment extends PreferenceFragment {//} implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        Preference myPref = (Preference) findPreference(getActivity().getString(R.string.preference_revert));
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                RevertDefaultDialog revertDefaultDialog = new RevertDefaultDialog();
                revertDefaultDialog.show(getFragmentManager(), "RevertDefaultDialog");
                return true;
            }
        });
    }
}
