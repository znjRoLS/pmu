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
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        levelName = intent.getStringExtra(MainActivity.INTENT_LEVEL_EXTRA_NAME);

        GameConfiguration.fillCurrentConfiguration(this);

        myImageView = (GameImageView) findViewById(R.id.myImageView);
        imageData = (GameImageData) myImageView.getImageData();
//        imageData.setScreenSize(getScreenSize());
//        imageData.updateRadius();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = getScreenSize().second/dm.xdpi;
        double y = getScreenSize().first/dm.ydpi;
        x *= 2.54;
        y *= 2.54;

//        Log.d("gravity", 9.81f / (x/100) + "");
//        Log.d("gravity", 9.81f / (x/100) * getScreenSize().second + "");
////        Log.d("gravity", 9.81f / (x/100) * getScreenSize().second / Math.pow(GameConfiguration.currentConfiguration.GAME_UPDATE_RATE,2) + "");
//        //TODO: refactor
//        float gravityByPixels = (float) (
//                9.81f / (x/100) * getScreenSize().second);// / Math.pow(GameConfiguration.currentConfiguration.GAME_UPDATE_RATE,2));

        float pixelsByMeter = getScreenSize().second / (float)(x/100);

        gameController = new GameController(this, this, myImageView, levelName);
        gameController.setPixelsRatio(pixelsByMeter);

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
        dialog.show(getFragmentManager(), "ScoreDialog");
    }

    private Pair<Integer, Integer> getScreenSize() {

        //TODO: imageview size, not screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return new Pair<Integer, Integer>(height, width);
    }

    @Override
    public void finish(){
        gameController.gameStop();
        super.finish();
    }

    @Override
    public void onDialogPositiveAction(String name) {

        Log.d("endgame", "herhe");

        Time time = new Time(gameController.getTimer().getTime());
        writeScore(name, time);
        Log.d("endgame", "herhe2");

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
        Log.d("endgame", "herhe3");
    }

}
