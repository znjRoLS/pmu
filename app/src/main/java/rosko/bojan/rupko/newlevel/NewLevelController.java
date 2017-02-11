package rosko.bojan.rupko.newlevel;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import rosko.bojan.rupko.imageview.Controller;
import rosko.bojan.rupko.imageview.Level;
import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/19/17.
 */

public class NewLevelController extends Controller implements View.OnTouchListener{

    private GestureDetector gestureDetector;

    private MyPointF lastLongPress;

    private NewLevelImageData newLevelImageData;

    private MyImageView myImageView;


    public NewLevelController(Context context, ViewInterface view, MyImageView myImageView) {
        super(context, view, myImageView);

        this.myImageView = myImageView;
        newLevelImageData = (NewLevelImageData) imageData;

        initGestureDetector();

        myImageView.setOnTouchListener(this);
    }

    private void initGestureDetector() {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent motionEvent) {
                newLevelImageData.removeDraggable(motionEvent.getActionIndex());
                lastLongPress = new MyPointF(motionEvent.getX(), motionEvent.getY());
                processLongPress();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);

//        Log.d("ontouch", "view " + view.getId() + " with height " + view.getHeight());
//        Log.d("ontouch", "motionEventtype " + motionEvent.getAction());
//        Log.d("ontouch", "motion index " + motionEvent.getActionIndex());

        //TODO: fix multiple touches
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
//                Log.d("ontouch", "asction down");
                newLevelImageData.addDraggable(motionEvent.getActionIndex(), motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
//                Log.d("ontouch", "asction up");
                if (newLevelImageData.checkDraggableExistant(motionEvent.getActionIndex())) {
                    if (!newLevelImageData.finishDraggable(motionEvent.getActionIndex())) {
                        Logger.throwError(context, "New wall would collide!", false);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d("ontouch", "asction move");
                newLevelImageData.updateDraggable(motionEvent.getActionIndex(), motionEvent.getX(), motionEvent.getY());
                break;
        }

        this.view.updateImageView();

        return true;
    }

    private void processLongPress() {

        if (newLevelImageData.tryRemoveElement(lastLongPress)) {
            Logger.throwError(context, "Element removed!", false);
        } else {
            openSpawnDialogue();
        }
    }

    private void openSpawnDialogue() {
        if (imageData.checkCollisions(lastLongPress)) {
            Logger.throwError(context, "New hole would collide!", false);
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

    public boolean saveLevel(String filename) {
        Level level = newLevelImageData.getLevel();
        if (level == null) {
            Logger.throwError(context, "Level not valid!", true);
            return false;
        }

        level.setName(filename);

        String levelSuffix = GameConfiguration.currentConfiguration.LEVEL_SUFIX;

        File internalDir = context.getFilesDir();

        File file = new File(internalDir, filename + levelSuffix);

        boolean fileExisted = file.exists();

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(level);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            Logger.throwError(context, "Problem with output stream!", true);
            e.printStackTrace();
        }

        if (fileExisted) {
            Logger.throwError(context, "Successfully overwritten existing level", false);
        }
        else {
            Logger.throwError(context, "Successfully saved level!", false);
        }

        saveBitmap(filename);

        return true;
    }

    private void saveBitmap(String filename) {
//        Bitmap bitmap = ((BitmapDrawable)myImageView.getDrawable()).getBitmap();
//        Drawable d = myImageView.getDrawable();
//        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap = myImageView.getDrawingCache();
        int bitmapSize = GameConfiguration.currentConfiguration.BITMAP_SIZE;
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmapSize, bitmapSize, false);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);

        File internalDir = context.getFilesDir();
        File file = new File(internalDir, filename + GameConfiguration.currentConfiguration.BITMAP_SUFIX);

        FileOutputStream bitmapOutStream = null;
        try {
            bitmapOutStream = new FileOutputStream(file);
            newBitmap.compress(
                    GameConfiguration.currentConfiguration.BITMAP_FORMAT,
                    GameConfiguration.currentConfiguration.BITMAP_COMPRESS_FACTOR,
                    bitmapOutStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bitmapOutStream != null) {
                    bitmapOutStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
