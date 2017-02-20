package rosko.bojan.rupko.game;

import rosko.bojan.rupko.imageview.Level;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/22/17.
 */

public class GameImageData extends ImageData {

    Ball ball;

    private float pixelsByMetersRatio = 0;

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

    public Ball.BallMovement moveBall(float dx, float dy, float dz, float deltaTime) {
        return ball.updateBallMovement(dx, dy, dz, deltaTime);
    }

    public void resetBall() {
        float ballRad = ball.getRadius();
        ball = new Ball(this);
        ball.setCenter(new MyPointF(
                startHole.getCenter().x,
                startHole.getCenter().y
        ));
        ball.setRadius(ballRad);
        ball.setPixelsByMetersRatio(pixelsByMetersRatio);
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
        currentRadius = GameConfiguration.currentConfiguration.HOLE_RADIUS_RATIO * smaller;
        currentRadius *= GameConfiguration.currentConfiguration.BALL_RADIUS_RATIO;

        ball.setRadius(currentRadius);
    }

    public void setPixelsRatio(float pixelsByMetersRatio) {
        this.pixelsByMetersRatio = pixelsByMetersRatio;
        ball.setPixelsByMetersRatio(pixelsByMetersRatio);
    }
}
