package rosko.bojan.rupko.imageview;

import java.io.Serializable;

/**
 * Created by rols on 1/22/17.
 */

public class MyPointF implements Serializable {

    private static final long serialVersionUID = 555555L;

    public float x;
    public float y;

    public MyPointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MyPointF() {
        x = y = 0;
    }


    public float getMagnitude() {
        return (float)Math.sqrt(Math.pow((double)x, 2) + Math.pow((double)y, 2));
    }

    public void add(MyPointF other) {
        x += other.x;
        y += other.y;
    }

    public void subtract(MyPointF other) {
        x -= other.x;
        y -= other.y;
    }

    public float getAngle() {
        return (float)Math.atan2(y,x);
    }

    public void rotate(float angle) {
        float oldX = x;
        float oldY = y;
        float cosine = (float)Math.cos((double)angle);
        float sine = (float)Math.sin((double)angle);
        x = oldX * cosine - oldY * sine;
        y = oldX * sine + oldY * cosine;
    }
}
