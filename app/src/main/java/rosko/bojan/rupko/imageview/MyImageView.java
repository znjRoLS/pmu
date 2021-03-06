package rosko.bojan.rupko.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/15/17.
 */

public class MyImageView extends ImageView {

    protected ImageData imageData;

    protected Paint holePaint;
    protected Paint startHolePaint;
    protected Paint endHolePaint;
    protected Paint wallPaint;


    public void MyImageViewConstructor() {

        holePaint = new Paint();
        holePaint.setColor(GameConfiguration.currentConfiguration.HOLE_COLOR);
        startHolePaint = new Paint();
        startHolePaint.setColor(GameConfiguration.currentConfiguration.START_HOLE_COLOR);
        endHolePaint = new Paint();
        endHolePaint.setColor(GameConfiguration.currentConfiguration.END_HOLE_COLOR);
        wallPaint = new Paint();
        wallPaint.setColor(GameConfiguration.currentConfiguration.WALL_COLOR);
    }

    public MyImageView(Context context) {
        super(context);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        for(int i = 0; i < attrs.getAttributeCount(); i ++) {
            String key = attrs.getAttributeName(i);
            String val = attrs.getAttributeValue(i);
            if (key.equalsIgnoreCase("imageDataClass")) {
                try {
                    imageData = (ImageData) Class.forName(val).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

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

        for (MyRectF wall : imageData.getWalls()) {
            canvas.drawRect(wall.left, wall.top, wall.right, wall.bottom, wallPaint);
        }

    }


    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
