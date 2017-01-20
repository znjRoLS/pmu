package rosko.bojan.rupko.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/20/17.
 */

public class MyPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
