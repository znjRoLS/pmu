package rosko.bojan.rupko.game;

import android.graphics.PointF;

import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;

/**
 * Created by rols on 1/22/17.
 */

public class GameImageData extends ImageData {

    Hole ball;

    public GameImageData() {
        super();

        ball = new Hole(startHole.getCenter(), Hole.Type.START);
        ball.setRadius(startHole.getRadius());
    }

    public Hole getBall() {
        return ball;
    }

    public void setBall(Hole ball) {
        this.ball = ball;
    }

    public void moveBall(float dx, float dy) {
        ball.getCenter().x += dx;
        ball.getCenter().y += dy;
    }
}
