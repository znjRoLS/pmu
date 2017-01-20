package rosko.bojan.rupko.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import rosko.bojan.rupko.GameConfiguration;
import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.newlevel.Hole;

/**
 * Created by rols on 1/15/17.
 */

public class MyImageView extends ImageView {

    ImageData imageData;

    Paint holePaint;
    Paint startHolePaint;
    Paint endHolePaint;
    Paint wallPaint;


    public void MyImageViewConstructor() {
        imageData = new ImageData();

        holePaint = new Paint();
        holePaint.setColor(GameConfiguration.HOLE_COLOR);
        startHolePaint = new Paint();
        startHolePaint.setColor(GameConfiguration.START_HOLE_COLOR);
        endHolePaint = new Paint();
        endHolePaint.setColor(GameConfiguration.END_HOLE_COLOR);
        wallPaint = new Paint();
        wallPaint.setColor(GameConfiguration.WALL_COLOR);
    }

    public MyImageView(Context context) {
        super(context);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        MyImageViewConstructor();
    }

    private void drawHole(Canvas canvas, Hole hole) {
        Paint paint;
        switch (hole.getType()) {
            case START: paint = startHolePaint; break;
            case HOLE: paint = holePaint; break;
            case END: paint = endHolePaint; break;
            default: paint = holePaint; break;
        }

        canvas.drawCircle(hole.getCenter().x, hole.getCenter().y, hole.getRadius(), paint);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Hole hole: imageData.getHoles()) {
            drawHole(canvas, hole);
        }
        if (imageData.getStartHole() != null) {
            drawHole(canvas, imageData.getStartHole());
        }
        if (imageData.getEndHole() != null) {
            drawHole(canvas, imageData.getEndHole());
        }

    }


    public ImageData getImageData() {
        return imageData;
    }
}
