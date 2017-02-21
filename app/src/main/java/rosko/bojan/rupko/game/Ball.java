package rosko.bojan.rupko.game;

import android.util.Log;

import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.imageview.MyRectF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 31.1.17..
 */

public class Ball {
    //todo: create countdown

    MyPointF center;
    float radius;

    private float pixelsByMetersRatio;
    private MyPointF velocity;
    private float GRAVITY_MAGNITUDE;
    private float BALL_TRACTION;
    private float BALL_BOUNCE;
    private float BALL_BOUNCE_THRESHOLD;
    private float SECOND_COLLISION_THRESHOLD;

    BallMovement bState;

    ImageData imageData;

    public enum BallMovement {
        DEFAULT, BOUNCE, SLIDE, START, END, HOLE
    }

    public Ball(ImageData imageData) {
        this.imageData = imageData;
        velocity = new MyPointF(0,0);
        GRAVITY_MAGNITUDE = GameConfiguration.currentConfiguration.GRAVITY_MAGNITUDE;
        BALL_TRACTION = GameConfiguration.currentConfiguration.BALL_TRACTION;
        BALL_BOUNCE = GameConfiguration.currentConfiguration.BALL_BOUNCE;
        BALL_BOUNCE_THRESHOLD = GameConfiguration.currentConfiguration.BALL_BOUNCE_THRESHOLD * pixelsByMetersRatio;
        SECOND_COLLISION_THRESHOLD = GameConfiguration.currentConfiguration.SECOND_COLLISION_THRESHOLD;
    }

    public void setPixelsByMetersRatio(float pixelsByMetersRatio){
        this.pixelsByMetersRatio = pixelsByMetersRatio;
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


    public BallMovement updateBallMovement(float gravityX, float gravityY, float gravityZ,  float deltaTime) {

        bState = BallMovement.DEFAULT;

        float accelerationStrength = pixelsByMetersRatio * GRAVITY_MAGNITUDE ;
        velocity.x += gravityX * accelerationStrength * deltaTime;
        velocity.y += gravityY * accelerationStrength * deltaTime;

        float tractionStrength = BALL_TRACTION * pixelsByMetersRatio;
        float tractionAcceleration = gravityZ * tractionStrength * deltaTime;

        if (velocity.getMagnitude() > tractionAcceleration) {
            float velocityAngle = velocity.getAngle();
            velocity.rotate(-velocityAngle);
            velocity.x -= tractionAcceleration;
            velocity.rotate(velocityAngle);
        }
        else {
            velocity.x = velocity.y = 0;
        }

        float oldX = center.x;
        float oldY = center.y;
        center.x += velocity.x * deltaTime;
        center.y += velocity.y * deltaTime;

        boolean collides = false;

        collides = bounceOfLimits() || collides;
        for(MyRectF wall : imageData.getWalls()) {
            collides = bounceOfWall(wall, false) || collides;
        }
        if (collides) {
            center.x = oldX + velocity.x * deltaTime;
            center.y = oldY + velocity.y * deltaTime;

            boolean collidesAgain = false;
            collidesAgain = bounceOfLimits() || collidesAgain;
            for(MyRectF wall : imageData.getWalls()) {
                collidesAgain = bounceOfWall(wall, true) || collidesAgain;
            }

            if (collidesAgain) {
                velocity.x = 0;
                velocity.y = 0;
                center.x = oldX;
                center.y = oldY;
            }

        }

        if (fallInHole(imageData.getStartHole())) {
            bState = BallMovement.START;
        }
        if (fallInHole(imageData.getEndHole())) {
            bState = BallMovement.END;
        }
        for(Hole hole : imageData.getHoles()) {
            if (fallInHole(hole)) {
                bState = BallMovement.HOLE;
            }
        }

        return bState;
    }

    private boolean fallInHole(Hole hole) {
        return dist(hole.getCenter(), center) <= Math.max(hole.getRadius(), radius);
    }

    private float bounce(float velocity, boolean positiveBounceDirection) {


        //bounce only in one direction
        if (positiveBounceDirection && velocity > 0) {
            return velocity;
        }
        if (!positiveBounceDirection && velocity < 0) {
            return velocity;
        }

        velocity *= - BALL_BOUNCE;
        if (Math.abs(velocity) < GameConfiguration.currentConfiguration.BALL_BOUNCE_THRESHOLD * pixelsByMetersRatio) {
            velocity = 0;
            if (bState != BallMovement.BOUNCE)
                bState = BallMovement.SLIDE;
        } else {
            bState = BallMovement.BOUNCE;
        }

        return velocity;
    }

    private boolean bounceOfLimits(){
        boolean bounced = false;
        if (center.x < radius) {
            velocity.x = bounce(velocity.x, true);
            bounced = true;
        }
        if (center.x > imageData.screenWidth - radius) {
            velocity.x = bounce(velocity.x, false);
            bounced = true;
        }
        if (center.y < radius) {
            velocity.y = bounce(velocity.y, true);
            bounced = true;
        }
        if (center.y > imageData.screenHeight - radius) {
            velocity.y = bounce(velocity.y, false);
            bounced = true;
        }

        return bounced;
    }

    private boolean bounceOfWall(MyRectF wall, boolean collidesAgain) {
        float xtopLeft = Math.max(center.x - radius, wall.left);
        float ytopLeft = Math.max(center.y - radius, wall.top);
        float xbottomRight = Math.min(center.x + radius, wall.right);
        float ybottomRight = Math.min(center.y + radius, wall.bottom);

        if (xtopLeft > xbottomRight - (SECOND_COLLISION_THRESHOLD * ((collidesAgain) ? 1 : 0)) || ytopLeft > ybottomRight - (SECOND_COLLISION_THRESHOLD * ((collidesAgain) ? 1 : 0))) {
            return false;
        }

        if (center.y >= wall.top && center.y <= wall.bottom && center.x < wall.left) {
            velocity.x = bounce(velocity.x, false);
            return true;
        }
        if (center.y >= wall.top && center.y <= wall.bottom && center.x > wall.right) {
            velocity.x = bounce(velocity.x, true);
            return true;
        }
        if (center.x >= wall.left && center.x <= wall.right && center.y < wall.top) {
            velocity.y = bounce(velocity.y, false);
            return true;
        }
        if (center.x >= wall.left && center.x <= wall.right && center.y > wall.bottom) {
            velocity.y = bounce(velocity.y, true);
            return true;
        }


        boolean collision = false;
        MyPointF otherPoint = null;

        MyPointF topLeft = new MyPointF(wall.left, wall.top);
        if (dist(topLeft, center) <= radius - (SECOND_COLLISION_THRESHOLD * ((collidesAgain) ? 1 : 0)) ) {
            collision = true;
            otherPoint = topLeft;
        }
        MyPointF topRight = new MyPointF(wall.right, wall.top);
        if (dist(topRight, center) <= radius - (SECOND_COLLISION_THRESHOLD * ((collidesAgain) ? 1 : 0)) ) {
            collision = true;
            otherPoint = topRight;
        }
        MyPointF bottomLeft = new MyPointF(wall.left, wall.bottom);
        if (dist(bottomLeft, center) <= radius - (SECOND_COLLISION_THRESHOLD * ((collidesAgain) ? 1 : 0)) ) {
            collision = true;
            otherPoint = bottomLeft;
        }
        MyPointF bottomRight = new MyPointF(wall.right, wall.bottom);
        if (dist(bottomRight, center) <= radius - (SECOND_COLLISION_THRESHOLD * ((collidesAgain) ? 1 : 0)) ) {
            collision = true;
            otherPoint = bottomRight;
        }

        if (!collision)
            return false;

        MyPointF resistanceVector = new MyPointF(otherPoint.x, otherPoint.y);
        resistanceVector.subtract(center);
        float resistanceAngle = resistanceVector.getAngle();
        velocity.rotate(-resistanceAngle);
        velocity.x = bounce(velocity.x, false);
        velocity.rotate(resistanceAngle);

        return true;
    }


    private float dist(MyPointF point1, MyPointF point2) {
        return (float)Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }
}
