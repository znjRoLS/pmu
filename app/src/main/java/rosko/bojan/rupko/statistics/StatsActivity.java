package rosko.bojan.rupko.statistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/21/17.
 */

public class StatsActivity extends AppCompatActivity {

    public static final String STATS_LEVEL_EXTRA = "rosko.bojan.rupko.stats_level_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        GameConfiguration.fillCurrentConfiguration(this);

        String levelName = getIntent().getStringExtra(STATS_LEVEL_EXTRA);

        ListView statsListView = (ListView) findViewById(R.id.statsListView);
    }
}
