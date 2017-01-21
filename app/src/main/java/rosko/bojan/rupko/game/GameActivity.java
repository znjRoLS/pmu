package rosko.bojan.rupko.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rosko.bojan.rupko.main.MainActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/19/17.
 */

public class GameActivity extends AppCompatActivity implements GameController.ViewInterface {

    GameController gameController;
    MyImageView myImageView;

    String levelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        levelName = intent.getStringExtra(MainActivity.INTENT_LEVEL_EXTRA_NAME);

        GameConfiguration.fillCurrentConfiguration(this);

        myImageView = (MyImageView) findViewById(R.id.myImageView);
        gameController = new GameController(this, this, myImageView, levelName);

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
        myImageView.invalidate();
    }
}
