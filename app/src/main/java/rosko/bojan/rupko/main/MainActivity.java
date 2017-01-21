package rosko.bojan.rupko.main;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.game.GameActivity;
import rosko.bojan.rupko.newlevel.NewElementDialog;
import rosko.bojan.rupko.newlevel.NewLevelActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.preferences.PreferencesActivity;
import rosko.bojan.rupko.statistics.StatsDbHelper;

public class MainActivity extends AppCompatActivity implements LevelEditDialog.ListDialogListener {

    ListView levelsListView;

    public final static String INTENT_LEVEL_EXTRA_NAME = "rosko.bojan.rupko.LEVEL_EXTRA";
    public final static String DIALOG_BUNDLE_LEVEL_NAME = "rosko.bojan.rupko.LEVEL_DIALOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameConfiguration.fillCurrentConfiguration(this);

        levelsListView = (ListView) findViewById(R.id.levelsListView);

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
            levels.add(getLevel(levelFile + GameConfiguration.currentConfiguration.LEVEL_SUFIX));
        }

        LevelListAdapter adapter = new LevelListAdapter(this, R.layout.row_level, levels);
        levelsListView.setAdapter(adapter);
        levelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.levelNameTextView);
                startNewGameActivity(textView.getText().toString());
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
    private Level getLevel(String levelFilename) {
        Level level = null;

        File file = new File(getFilesDir(), levelFilename);

        try {
            FileInputStream inputStream = new FileInputStream(file);
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
//        Intent intent = new Intent(this, StatsActivity.class);
//        startActivity(intent);
    }

    private void startPreferencesActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    private void startNewGameActivity(String level) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(INTENT_LEVEL_EXTRA_NAME, level);
        startActivity(intent);
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
