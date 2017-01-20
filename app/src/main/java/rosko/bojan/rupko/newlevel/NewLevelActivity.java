package rosko.bojan.rupko.newlevel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;

import rosko.bojan.rupko.Controller;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyImageView;

/**
 * Created by rols on 1/19/17.
 */

public class NewLevelActivity extends AppCompatActivity implements Controller.ViewInterface, NewElementDialog.ListDialogListener {

    NewLevelController controller;
    MyImageView myImageView;
    ImageData imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_level);

        GameConfiguration.fillCurrentConfiguration(this);

        myImageView = (MyImageView) findViewById(R.id.myImageView);
        imageData = myImageView.getImageData();
        imageData.setScreenSize(getScreenSize());

        controller = new NewLevelController(this, this, myImageView);
    }


    @Override
    public void updateImageView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myImageView.invalidate();
            }
        });
    }

    @Override
    public void onDialogItemClick(Hole.Type itemType) {
        controller.newItem(itemType);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //TODO: proper serialization
        savedInstanceState.putSerializable("data", imageData);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageData = (ImageData) savedInstanceState.getSerializable("data");

        imageData.setScreenSize(getScreenSize());
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
}
