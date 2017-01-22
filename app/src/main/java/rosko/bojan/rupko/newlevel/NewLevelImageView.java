package rosko.bojan.rupko.newlevel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;

import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/20/17.
 */

public class NewLevelImageView extends MyImageView {

    NewLevelImageData newLevelImageData;

    protected Paint draggablePaint;

    private void MyNewLevelConstructior() {
        imageData = new NewLevelImageData();

        newLevelImageData = (NewLevelImageData) imageData;

        draggablePaint = new Paint();
        draggablePaint.setColor(GameConfiguration.currentConfiguration.DRAGGABLE_COLOR);

    }

    public NewLevelImageView(Context context) {
        super(context);
        MyNewLevelConstructior();
    }

    public NewLevelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyNewLevelConstructior();
    }

    public NewLevelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        MyNewLevelConstructior();
    }

    public NewLevelImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        MyNewLevelConstructior();
    }

    private void drawWall(Canvas canvas, MyPointF startPoint, MyPointF endPoint, Paint paint) {
        float left = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
        float right = startPoint.x < endPoint.x ? endPoint.x : startPoint.x;
        float top = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
        float bottom = startPoint.y < endPoint.y ? endPoint.y : startPoint.y;

        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        MyPointF[] draggablesStart = newLevelImageData.getDraggables();
        MyPointF[] draggablesEnd = newLevelImageData.getDraggablesEnd();
        //TODO: hardcoded
        for (int i = 0; i < 20; i ++) {
            if (draggablesStart[i] != null) {
                drawWall(canvas, draggablesStart[i], draggablesEnd[i], draggablePaint);
            }
        }

        for (RectF wall : imageData.getWalls()) {
            canvas.drawRect(wall, wallPaint);
        }
    }
}
