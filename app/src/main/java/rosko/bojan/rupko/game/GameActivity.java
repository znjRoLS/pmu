package rosko.bojan.rupko.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;

import rosko.bojan.rupko.main.MainActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/19/17.
 */

public class GameActivity extends AppCompatActivity implements GameController.ViewInterface {

    GameController gameController;
    GameImageView myImageView;
    GameImageData imageData;

    String levelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        levelName = intent.getStringExtra(MainActivity.INTENT_LEVEL_EXTRA_NAME);

        GameConfiguration.fillCurrentConfiguration(this);

        myImageView = (GameImageView) findViewById(R.id.myImageView);
        imageData = (GameImageData) myImageView.getImageData();
        imageData.setScreenSize(getScreenSize());
        imageData.updateRadius();

        gameController = new GameController(this, this, myImageView, levelName);
        gameController.startLevel();
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
        gameController.gameEnd();
        super.finish();
    }
}
