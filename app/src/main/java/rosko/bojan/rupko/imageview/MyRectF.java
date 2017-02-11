package rosko.bojan.rupko.imageview;

import android.graphics.RectF;

import java.io.Serializable;

/**
 * Created by rols on 31.1.17..
 */

public class MyRectF implements Serializable {

    private static final long serialVersionUID = 55555L;

    public float left, top, right, bottom;

    public MyRectF(float left, float top, float right, float bottom) {
        this.bottom = bottom;
        this.left = left;
        this.top = top;
        this.right = right;
    }

    public MyRectF() {
        bottom = left = top = right = 0;
    }

    public boolean collides(MyRectF other) {
        float interLeft = Math.max(left, other.left);
        float interRight = Math.min(right, other.right);
        float interTop = Math.max(top, other.top);
        float interBottom = Math.min(bottom, other.bottom);

        return (interLeft <= interRight && interTop <= interBottom);

    }

    public boolean collides(MyPointF point) {
        return point.x <= right && point.x >= left && point.y <= bottom && point.y >= top;
    }

}
