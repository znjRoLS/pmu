package rosko.bojan.rupko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import rosko.bojan.rupko.newlevel.NewLevelActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.preferences.PreferencesActivity;

public class MainActivity extends AppCompatActivity {

    ListView levelsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameConfiguration.fillCurrentConfiguration(this);

        levelsListView = (ListView) findViewById(R.id.levelsListView);

        inflateLevels();
    }

    private void inflateLevels() {



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_level_menu_item:
                startNewLevelActivity();
                return true;
            case R.id.show_statistics_menu_item:
                startStatisticsActivity();
                return true;
            case R.id.show_preferences_menu_item:
                startPreferencesActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startNewLevelActivity() {
        Intent intent = new Intent(this, NewLevelActivity.class);
        startActivity(intent);
    }

    private void startStatisticsActivity() {
        Logger.throwError(this, "Not implemented yet!");
//        Intent intent = new Intent(this, StatisticsActivity.class);
//        startActivity(intent);
    }

    private void startPreferencesActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }
}
