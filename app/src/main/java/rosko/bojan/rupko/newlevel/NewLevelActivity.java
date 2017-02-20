package rosko.bojan.rupko.newlevel;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import rosko.bojan.rupko.game.ScoreDialog;
import rosko.bojan.rupko.imageview.Controller;
import rosko.bojan.rupko.imageview.Level;
import rosko.bojan.rupko.main.MainActivity;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;

/**
 * Created by rols on 1/19/17.
 */

public class NewLevelActivity extends AppCompatActivity implements
        Controller.ViewInterface,
        NewElementDialog.ListDialogListener,
        SaveDialog.DialogActionListener
{

    NewLevelController controller;
    NewLevelImageView myImageView;
    ImageData imageData;

    private String levelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameConfiguration.fillCurrentConfiguration(this);

        setContentView(R.layout.activity_new_level);

        myImageView = (NewLevelImageView) findViewById(R.id.myImageView);
        myImageView.setDrawingCacheEnabled(true);
        imageData = myImageView.getImageData();

        levelName = getIntent().getStringExtra(MainActivity.INTENT_LEVEL_EXTRA_NAME);

        //todo: not here
        final NewLevelActivity that = this;
        //update sizes
        myImageView.post(new Runnable() {
            @Override
            public void run() {
                imageData.setScreenSize(new Pair<Integer, Integer>(that.myImageView.getHeight(), that.myImageView.getWidth()));
                if (levelName != null) {
                    imageData.loadLevel(Level.loadLevel(that, levelName));
                }
                imageData.updateRadius();

                myImageView.invalidate();
            }
        });

        controller = new NewLevelController(this, this, myImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newlevel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_newlevel_menu_item:
                openSaveDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSaveDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SaveDialog();

        Bundle args = new Bundle();
        if (levelName != null) {
            args.putString(
                    SaveDialog.LEVEL_NAME_EXTRA_NAME,
                    levelName
            );
        }
        dialog.setArguments(args);

        dialog.show(getFragmentManager(), "SaveDialog");
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

        savedInstanceState.putSerializable("data", imageData);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageData = (ImageData) savedInstanceState.getSerializable("data");

        final NewLevelActivity that = this;
        //update sizes
        myImageView.post(new Runnable() {
            @Override
            public void run() {
                myImageView.setImageData(imageData);
                controller.setNewLevelImageData((NewLevelImageData)imageData);

                imageData.setScreenSize(new Pair<Integer, Integer>(that.myImageView.getHeight(), that.myImageView.getWidth()));
                imageData.updateRadius();
                myImageView.invalidate();
            }
        });
    }

    @Override
    public void onDialogPositiveAction(String name) {
        if (controller.saveLevel(name)) {
            finish();
        }
    }
}
