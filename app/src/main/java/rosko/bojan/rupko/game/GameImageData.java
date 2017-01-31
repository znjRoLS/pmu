package rosko.bojan.rupko.game;

import android.graphics.PointF;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;

/**
 * Created by rols on 1/22/17.
 */

public class GameImageData extends ImageData {

    Ball ball;

    public GameImageData() {
        super();
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void moveBall(float dx, float dy) {

        ball.getCenter().x += dx;
        ball.getCenter().y += dy;
    }

    @Override
    public void loadLevel(Level level) {
        super.loadLevel(level);

        ball = new Ball(this);
        ball.setCenter(new MyPointF(
                startHole.getCenter().x,
                startHole.getCenter().y
        ));
        ball.setRadius(startHole.getRadius());
    }

    @Override
    public void updateRadius() {
        super.updateRadius();

        ball.setRadius(startHole.getRadius());
    }
}
