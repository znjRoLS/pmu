package rosko.bojan.rupko.newlevel;

import android.graphics.PointF;

import java.io.Serializable;

import rosko.bojan.rupko.GameConfiguration;

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
