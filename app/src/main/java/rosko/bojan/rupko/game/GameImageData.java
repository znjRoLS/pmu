package rosko.bojan.rupko.game;

import android.graphics.PointF;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/22/17.
 */

public class GameImageData extends ImageData {

    Ball ball;

    public GameImageData() {
        super();

        ball = new Ball(this);
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void moveBall(float dx, float dy) {
        ball.updateBallMovement(dx,dy);
    }

    @Override
    public void loadLevel(Level level) {
        super.loadLevel(level);

        ball.setCenter(new MyPointF(
                startHole.getCenter().x,
                startHole.getCenter().y
        ));
    }

    @Override
    public void updateRadius() {
        super.updateRadius();

        int smaller = screenHeight>screenWidth?screenWidth:screenHeight;
        currentRadius = GameConfiguration.currentConfiguration.HOLE_RADIUS_PERCENTAGE * smaller;

        ball.setRadius(currentRadius);
    }
}
