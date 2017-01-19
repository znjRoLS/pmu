package rosko.bojan.rupko.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/19/17.
 */

public class GameActivity extends AppCompatActivity implements GameController.ViewInterface {

    GameController gameController;
    MyImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImageView = (MyImageView) findViewById(R.id.myImageView);
        gameController = new GameController(this, this, myImageView);
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
