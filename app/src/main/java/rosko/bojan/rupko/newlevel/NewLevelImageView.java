package rosko.bojan.rupko.newlevel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;

import rosko.bojan.rupko.imageview.MyImageView;

/**
 * Created by rols on 1/20/17.
 */

public class NewLevelImageView extends MyImageView {

    NewLevelImageData newLevelImageData;

    public NewLevelImageView(Context context) {
        super(context);


    }

    public NewLevelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageData = new NewLevelImageData();

        newLevelImageData = (NewLevelImageData) imageData;
    }

    public NewLevelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewLevelImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void drawDraggables(Canvas canvas, PointF startPoint, PointF endPoint) {
        float left = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
        float right = startPoint.x < endPoint.x ? endPoint.x : startPoint.x;
        float top = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
        float bottom = startPoint.y < endPoint.y ? endPoint.y : startPoint.y;

        canvas.drawRect(left, top, right, bottom, wallPaint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        PointF[] draggablesStart = newLevelImageData.getDraggables();
        PointF[] draggablesEnd = newLevelImageData.getDraggablesEnd();
        //TODO: hardcoded
        for (int i = 0; i < 20; i ++) {
            if (draggablesStart[i] != null) {
                drawDraggables(canvas, draggablesStart[i], draggablesEnd[i]);
            }
        }
    }
}
