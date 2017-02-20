package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/20/17.
 */

public class PreferencesActivity extends PreferenceActivity implements RevertDefaultDialog.DialogActionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Display the fragment as the main content.
//        getFragmentManager().beginTransaction()
//                .replace(R.id.preferencesFrameLayout, new MyPreferenceFragment())
//                .commit();
//        GameConfiguration.fillCurrentConfiguration(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.restore_default_menu_item:
                GameConfiguration.restoreDefaultPreferences(this);
                return true;
//            case R.id.save_preferences_menu_item:
//                GameConfiguration.savePreferences(this);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveAction() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();

        finish();
        startActivity(getIntent());
    }


}
