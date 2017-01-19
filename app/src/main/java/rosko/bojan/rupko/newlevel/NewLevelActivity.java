package rosko.bojan.rupko.newlevel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import rosko.bojan.rupko.Controller;
import rosko.bojan.rupko.R;
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

        myImageView = (MyImageView) findViewById(R.id.myImageView);
        imageData = myImageView.getImageData();

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
}
