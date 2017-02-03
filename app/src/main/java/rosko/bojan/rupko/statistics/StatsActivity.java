package rosko.bojan.rupko.statistics;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Locale;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/21/17.
 */

public class StatsActivity extends AppCompatActivity {

    public static final String STATS_LEVEL_EXTRA = "rosko.bojan.rupko.stats_level_extra";

    private String levelName;
    private ListView statsListView;
    private StatsDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        GameConfiguration.fillCurrentConfiguration(this);

        levelName = getIntent().getStringExtra(STATS_LEVEL_EXTRA);
        statsListView = (ListView) findViewById(R.id.statsListView);
        dbHelper = new StatsDbHelper(this);

        inflateListView();
    }

    private void inflateListView() {
        Log.d("statsinflate", "hreh");
        Log.d("statsinflate", "levelname " + levelName);

        Cursor cursor = dbHelper.getOrderedStatsForLevel(levelName);

        ListAdapter adapter = new CursorAdapter(this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                return LayoutInflater.from(context).inflate(R.layout.row_stats, viewGroup, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView tvusername = (TextView) view.findViewById(R.id.scoreUsernameTextView);
                TextView tvtime = (TextView) view.findViewById(R.id.scoreTimeTextView);
                // Extract properties from cursor
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                long time = cursor.getLong(cursor.getColumnIndexOrThrow("time"));
                // Populate fields with extracted properties
                tvusername.setText(username);

                long minutes = time / 60000;
                long seconds = time / 1000 % 60;
                long millis = time % 1000;

                tvtime.setText(String.format(Locale.getDefault(), "%02d:%02d:%03d", minutes, seconds, millis));
            }
        };

        statsListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reset_stats_menu_item:
                dbHelper.dropLevelStats(levelName);
                finish();
                return true;
            case R.id.reset_all_stats_menu_item:
                dbHelper.dropAllStats();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
