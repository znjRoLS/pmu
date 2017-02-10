package rosko.bojan.rupko.main;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import rosko.bojan.rupko.imageview.Level;
import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.game.GameActivity;
import rosko.bojan.rupko.game.GameLandActivity;
import rosko.bojan.rupko.newlevel.NewLevelActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.preferences.PreferencesActivity;
import rosko.bojan.rupko.statistics.StatsActivity;
import rosko.bojan.rupko.statistics.StatsDbHelper;

public class MainActivity extends AppCompatActivity implements LevelEditDialog.ListDialogListener {

    ListView levelsListView;

    public final static String INTENT_LEVEL_EXTRA_NAME = "rosko.bojan.rupko.LEVEL_EXTRA";
    public final static String DIALOG_BUNDLE_LEVEL_NAME = "rosko.bojan.rupko.LEVEL_DIALOG";

    private boolean statsWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statsWaiting = false;

        GameConfiguration.fillCurrentConfiguration(this);

        levelsListView = (ListView) findViewById(R.id.levelsListView);

        Log.d("imherh", "am i here?");
    }

    @Override
    protected void onResume() {
        super.onResume();

        inflateLevels();
    }


    private void inflateLevels() {

        File internalDir = getFilesDir();

        String levelRegex = ".*" + GameConfiguration.currentConfiguration.LEVEL_SUFIX.replace(".", "\\.");
        final Pattern levelPattern = Pattern.compile(levelRegex);
        File[] levelList = internalDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return levelPattern.matcher(file.getName()).matches();
            }
        });

        ArrayList<Level> levels = new ArrayList<>();
        for (File levelFile: levelList) {
            levels.add(getLevel(levelFile));
        }

        LevelListAdapter adapter = new LevelListAdapter(this, R.layout.row_level, levels);
        levelsListView.setAdapter(adapter);
        levelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (statsWaiting) {
                    statsWaiting = false;
                    TextView textView = (TextView) view.findViewById(R.id.levelNameTextView);
                    startStatsActivity(textView.getText().toString());
                }
                else {
                    TextView textView = (TextView) view.findViewById(R.id.levelNameTextView);
                    //TODO: omg, please dont mess up with textviews
                    startNewGameActivity(textView.getText().toString());
                }
            }
        });
        levelsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.levelNameTextView);
                openEditLevelDialog(textView.getText().toString());

//                return false;
                return true;
            }
        });
    }

    //TODO: somewhere else?
    private Level getLevel(File levelFile) {
        Level level = null;

        //File file = new File(getFilesDir(), levelFilename);

        try {
            FileInputStream inputStream = new FileInputStream(levelFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            level = (Level) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Logger.throwError(this, "Problem with input stream!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.throwError(this, "Problem with class not found!");
            e.printStackTrace();
        }

        return level;
    }

    @Override
    public void onBackPressed() {
        if (statsWaiting) {
            //TODO: lepse pisanije
            Logger.throwError(this, "Got out from STATS mode");
            statsWaiting = false;
        }
        else {
            super.onBackPressed();
        }
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
                Logger.throwError(this, "Create a new level!");
                startNewLevelActivity();
                return true;
            case R.id.show_statistics_menu_item:
                statsWaiting = true;
                Logger.throwError(this, "Click on level to show stats, or press back to return to normal mode");
                return true;
            case R.id.show_preferences_menu_item:
                Logger.throwError(this, "Change preferences");
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

    private void startPreferencesActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    private void startStatsActivity(String level) {
        Intent intent = new Intent(this, StatsActivity.class);
        intent.putExtra(StatsActivity.STATS_LEVEL_EXTRA, level);
        startActivity(intent);
    }

    private void startNewGameActivity(String level) {
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(INTENT_LEVEL_EXTRA_NAME, level);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, GameLandActivity.class);
            intent.putExtra(INTENT_LEVEL_EXTRA_NAME, level);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    private void openEditLevelDialog(String level) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new LevelEditDialog();
        Bundle bundle = new Bundle();
        bundle.putString(DIALOG_BUNDLE_LEVEL_NAME,level);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "LevelEditDialog");
    }

    @Override
    public void onDialogEditClick(String levelName) {
        Intent intent = new Intent(this, NewLevelActivity.class);
        intent.putExtra(INTENT_LEVEL_EXTRA_NAME, levelName);
        startActivity(intent);
    }

    @Override
    public void onDialogDeleteClick(String levelName) {

        //delete stats from database
        StatsDbHelper dbHelper = new StatsDbHelper(this);
        dbHelper.dropLevelStats(levelName);

        //delete level file
        File dir = getFilesDir();
        File file = new File(dir, levelName + GameConfiguration.currentConfiguration.LEVEL_SUFIX);
        boolean deleted = file.delete();

        //update list view
        inflateLevels();
    }
}
