package rosko.bojan.rupko.newlevel;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

import rosko.bojan.rupko.Controller;
import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.MyImageView;

/**
 * Created by rols on 1/19/17.
 */

public class NewLevelController extends Controller implements View.OnTouchListener{

    private GestureDetector gestureDetector;

    private PointF lastLongPress;

    private NewLevelImageData newLevelImageData;


    public NewLevelController(Context context, ViewInterface view, MyImageView myImageView) {
        super(context, view, myImageView);

        newLevelImageData = (NewLevelImageData) imageData;

        initGestureDetector();

        myImageView.setOnTouchListener(this);
    }

    private void initGestureDetector() {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent motionEvent) {
                lastLongPress = new PointF(motionEvent.getX(), motionEvent.getY());
                openSpawnDialogue();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);

        Log.d("ontouch", "view " + view.getId() + " with height " + view.getHeight());
        Log.d("ontouch", "motionEventtype " + motionEvent.getAction());
        Log.d("ontouch", "motion index " + motionEvent.getActionIndex());

        //TODO: fix multiple touches
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("ontouch", "asction down");
                newLevelImageData.addDraggable(motionEvent.getActionIndex(), motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("ontouch", "asction up");
                newLevelImageData.finishDraggable(motionEvent.getActionIndex());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("ontouch", "asction move");
                newLevelImageData.updateDraggable(motionEvent.getActionIndex(), motionEvent.getX(), motionEvent.getY());
                break;
        }

        this.view.updateImageView();

        return true;
    }

    private void openSpawnDialogue() {
        if (imageData.checkCollisions(lastLongPress)) {
            Logger.throwError(context, "New hole would collide!");
        }
        else {
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new NewElementDialog();
            Activity activity = (Activity) context;
            dialog.show(activity.getFragmentManager(), "NewElementDialog");
        }
    }

    public void newItem(Hole.Type newItem) {
        switch (newItem) {
            case START:
                imageData.setStartHole(new Hole(lastLongPress, Hole.Type.START));
                break;
            case HOLE:
                imageData.addHole(new Hole(lastLongPress, Hole.Type.HOLE));
                break;
            case END:
                imageData.setEndHole(new Hole(lastLongPress, Hole.Type.END));
                break;
        }

        view.updateImageView();
    }
}
