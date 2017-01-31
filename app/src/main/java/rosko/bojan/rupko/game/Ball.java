package rosko.bojan.rupko.game;

import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.imageview.MyRectF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 31.1.17..
 */

public class Ball {
    MyPointF center;
    float radius;

    private MyPointF velocity;
    private float GRAVITY_MAGNITUDE;
    private float BALL_TRACTION;
    private float BALL_BOUNCE;

    ImageData imageData;

    public Ball(ImageData imageData) {
        this.imageData = imageData;
        velocity = new MyPointF(0,0);
        GRAVITY_MAGNITUDE = GameConfiguration.currentConfiguration.GRAVITY_MAGNITUDE;
        BALL_TRACTION = GameConfiguration.currentConfiguration.BALL_TRACTION;
        BALL_BOUNCE = GameConfiguration.currentConfiguration.BALL_BOUNCE;
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


    public void updateBallMovement(float gravityX, float gravityY) {

        velocity.x *= 1 - BALL_TRACTION;
        velocity.y *= 1 - BALL_TRACTION;

        velocity.x += gravityX * GRAVITY_MAGNITUDE;
        velocity.y += gravityY * GRAVITY_MAGNITUDE;

        float oldX = center.x;
        float oldY = center.y;

        center.x += velocity.x;
        center.y += velocity.y;

        boolean collides = false;

        for(MyRectF wall : imageData.getWalls()) {
            collides = collides || bounceOfWall(wall);
        }

        if (collides) {
            center.x = oldX + velocity.x;
            center.y = oldY + velocity.y;
        }

    }

    private boolean bounceOfWall(MyRectF wall) {
        float xtopLeft = Math.max(center.x - radius, wall.left);
        float ytopLeft = Math.max(center.y - radius, wall.top);
        float xbottomRight = Math.min(center.x + radius, wall.right);
        float ybottomRight = Math.min(center.y + radius, wall.bottom);

        if (xtopLeft > xbottomRight || ytopLeft > ybottomRight) {
            return false;
        }

        if (center.y >= wall.top && center.y <= wall.bottom) {
            velocity.x *= - BALL_BOUNCE;
            return true;
        }

        if (center.x >= wall.left && center.x <= wall.right) {
            velocity.y *= - BALL_BOUNCE;
            return true;
        }

        boolean collision = false;
        MyPointF otherPoint = null;

        MyPointF topLeft = new MyPointF(wall.left, wall.top);
        if (dist(topLeft, center) <= radius) {
            collision = true;
            otherPoint = topLeft;
        }
        MyPointF topRight = new MyPointF(wall.right, wall.top);
        if (dist(topRight, center) <= radius) {
            collision = true;
            otherPoint = topRight;
        }
        MyPointF bottomLeft = new MyPointF(wall.left, wall.bottom);
        if (dist(bottomLeft, center) <= radius) {
            collision = true;
            otherPoint = bottomLeft;
        }
        MyPointF bottomRight = new MyPointF(wall.right, wall.bottom);
        if (dist(bottomRight, center) <= radius) {
            collision = true;
            otherPoint = bottomRight;
        }

        if (!collision)
            return false;

        MyPointF resistanceVector = new MyPointF(otherPoint.x, otherPoint.y);
        resistanceVector.subtract(center);
        float resistanceAngle = resistanceVector.getAngle();
        velocity.rotate(-resistanceAngle);
        velocity.x *= -BALL_BOUNCE;
        velocity.rotate(resistanceAngle);

        return true;
    }


    private float dist(MyPointF point1, MyPointF point2) {
        return (float)Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }
}
