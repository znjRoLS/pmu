package rosko.bojan.rupko.newlevel;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import rosko.bojan.rupko.imageview.Controller;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_level);

        GameConfiguration.fillCurrentConfiguration(this);

        myImageView = (NewLevelImageView) findViewById(R.id.myImageView);
        myImageView.setDrawingCacheEnabled(true);

        imageData = myImageView.getImageData();
        imageData.setScreenSize(getScreenSize());
        imageData.updateRadius();

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

        //TODO: proper serialization
        savedInstanceState.putSerializable("data", imageData);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageData = (ImageData) savedInstanceState.getSerializable("data");

        imageData.setScreenSize(getScreenSize());
        imageData.updateRadius();
    }

    //TODO : refactor
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
    public void onDialogPositiveAction(String name) {
        if (controller.saveLevel(name)) {
            finish();
        }
    }
}
