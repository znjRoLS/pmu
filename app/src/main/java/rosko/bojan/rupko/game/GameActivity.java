package rosko.bojan.rupko.game;

/**
 * Created by rols on 1/19/17.
 */

public class GameActivity extends GameAbstractActivity {
    @Override
    protected Ball.BallMovement moveBall(float currentX, float currentY, float currentZ, float gameUpdateMs) {
        return imageData.moveBall(-currentX, currentY, currentZ, gameUpdateMs);
    }
}
