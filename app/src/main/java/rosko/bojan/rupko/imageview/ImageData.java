package rosko.bojan.rupko.imageview;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by rols on 1/15/17.
 */

public class ImageData {

    PointF point;

    public ImageData() {

        point = new PointF(300,300);
    }

    public PointF getCircleCenter() {
        return point;
    }

    public void incCircle(float dx, float dy) {
        point.x += dx;
        point.y += dy;
    }
}
