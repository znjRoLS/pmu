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

    PointF center;
    float radius;
    Type type;



    public boolean collides(Hole otherHole) {
        return dist(center, otherHole.center) <= radius + otherHole.radius;
    }

    public boolean collides(RectF rectangle) {
        float xtopLeft = Math.max(center.x - radius, rectangle.left);
        float ytopLeft = Math.max(center.y - radius, rectangle.top);
        float xbottomRight = Math.min(center.x + radius, rectangle.right);
        float ybottomRight = Math.min(center.y + radius, rectangle.bottom);

        if (xtopLeft > xbottomRight || ytopLeft > ybottomRight) {
            return false;
        }

        PointF topLeft = new PointF(rectangle.left, rectangle.top);
        PointF topRight = new PointF(rectangle.right, rectangle.top);
        PointF bottomLeft = new PointF(rectangle.left, rectangle.bottom);
        PointF bottomRight = new PointF(rectangle.right, rectangle.bottom);

        if (
                dist(topLeft, center) <= radius ||
                dist(topRight, center) <= radius ||
                dist(bottomLeft, center) <= radius ||
                dist(bottomRight, center) <= radius
        ) {
            return true;
        }



    }

    private float dist(PointF point1, PointF point2) {
        return (float)Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    public Hole(PointF center, Type type) {
        this.center = center;
        this.type = type;

        radius = 30f;
//        radius = GameConfiguration.HOLE_RADIUS_PERCENTAGE;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public PointF getCenter() {
        return center;
    }

    public void setCenter(PointF center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
