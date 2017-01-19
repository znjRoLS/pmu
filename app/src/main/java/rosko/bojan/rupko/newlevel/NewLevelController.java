package rosko.bojan.rupko.newlevel;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import rosko.bojan.rupko.Controller;
import rosko.bojan.rupko.imageview.MyImageView;

/**
 * Created by rols on 1/19/17.
 */

public class NewLevelController extends Controller implements View.OnTouchListener{

    private GestureDetector gestureDetector;

    public NewLevelController(Context context, ViewInterface view, MyImageView myImageView) {
        super(context, view, myImageView);

        initGestureDetector();

        myImageView.setOnTouchListener(this);

    }

    private void initGestureDetector() {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent motionEvent) {
                Log.d("longpress", motionEvent.toString());
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);

        Log.d("ontouch", "view " + view.getId() + " with height " + view.getHeight());
        Log.d("ontouch", "motionEventtype " + motionEvent.getAction());
        Log.d("ontouch", "motionEventtype down " + MotionEvent.ACTION_DOWN);

        return false;
    }
}
