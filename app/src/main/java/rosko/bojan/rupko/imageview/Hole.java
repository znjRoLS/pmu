package rosko.bojan.rupko.imageview;

import android.graphics.PointF;
import android.graphics.RectF;

import java.io.Serializable;

/**
 * Created by rols on 1/19/17.
 */

public class Hole implements Serializable{
    public enum Type {
        START, HOLE, END
    }

    MyPointF center;
    float radius;
    Type type;


    public boolean collides(MyPointF point) {
        return dist(center, point) <= radius;
    }

    public boolean collides(Hole otherHole) {
        return dist(center, otherHole.center) <= radius + otherHole.radius;
    }

    public boolean collides(MyRectF rectangle) {
        float xtopLeft = Math.max(center.x - radius, rectangle.left);
        float ytopLeft = Math.max(center.y - radius, rectangle.top);
        float xbottomRight = Math.min(center.x + radius, rectangle.right);
        float ybottomRight = Math.min(center.y + radius, rectangle.bottom);

        if (xtopLeft > xbottomRight || ytopLeft > ybottomRight) {
            return false;
        }

        if (center.y >= rectangle.top && center.y <= rectangle.bottom)
            return true;
        if (center.x >= rectangle.left && center.x <= rectangle.right)
            return true;

        MyPointF topLeft = new MyPointF(rectangle.left, rectangle.top);
        MyPointF topRight = new MyPointF(rectangle.right, rectangle.top);
        MyPointF bottomLeft = new MyPointF(rectangle.left, rectangle.bottom);
        MyPointF bottomRight = new MyPointF(rectangle.right, rectangle.bottom);

        return (
                dist(topLeft, center) <= radius ||
                dist(topRight, center) <= radius ||
                dist(bottomLeft, center) <= radius ||
                dist(bottomRight, center) <= radius
        );
    }

    private float dist(MyPointF point1, MyPointF point2) {
        return (float)Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    public Hole(MyPointF center, Type type) {
        this.center = center;
        this.type = type;

        radius = 30f;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public MyPointF getCenter() {
        return center;
    }

    public void setCenter(MyPointF center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
