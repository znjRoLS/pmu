package rosko.bojan.rupko.game;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import java.sql.Time;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.main.MainActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.statistics.StatsActivity;
import rosko.bojan.rupko.statistics.StatsDbHelper;

/**
 * Created by rols on 2/10/17.
 */

public abstract class GameAbstractActivity extends AppCompatActivity implements GameController.ViewInterface, ScoreDialog.DialogActionListener {

    GameController gameController;
    GameImageView myImageView;
    GameImageData imageData;

    String levelName;

    // implemented separately because of screen orientation;
    protected abstract Ball.BallMovement moveBall(float currentX, float currentY, float currentZ, float gameUpdateMs);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameConfiguration.fillCurrentConfiguration(this);

        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        levelName = intent.getStringExtra(MainActivity.INTENT_LEVEL_EXTRA_NAME);

        myImageView = (GameImageView) findViewById(R.id.myImageView);
        imageData = (GameImageData) myImageView.getImageData();

        gameController = new GameController(this, this, myImageView, levelName);

        //starting level
        myImageView.post(new Runnable() {
            @Override
            public void run() {
                gameController.startLevel();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameController.onPause();
    }

    @Override
    public void updateView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myImageView.invalidate();
            }
        });
    }

    @Override
    public void showEndGameDialog() {
        DialogFragment dialog = new ScoreDialog();
        dialog.setCancelable(false);

        Bundle args = new Bundle();
        args.putString(
                ScoreDialog.DIALOG_TIME_EXTRA_NAME,
                ((TextView)findViewById(R.id.timerTextView)).getText().toString()
        );
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "ScoreDialog");
    }

    @Override
    public void finish(){
        gameController.gameStop();
        super.finish();
    }

    @Override
    public void onDialogPositiveAction(String name) {
        Time time = new Time(gameController.getTimer().getTime());
        writeScore(name, time);

        Intent intent = new Intent(this, StatsActivity.class);
        intent.putExtra(StatsActivity.STATS_LEVEL_EXTRA, levelName);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeAction() {
        finish();
    }

    public void writeScore(String username, Time time) {

        StatsDbHelper dbHelper = new StatsDbHelper(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper.insertNewStat(levelName, username, time);
    }
}
