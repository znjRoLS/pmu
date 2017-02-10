package rosko.bojan.rupko.game;
/**
 * Created by rols on 2/10/17.
 */

public class GameLandActivity extends GameAbstractActivity{
    @Override
    protected Ball.BallMovement moveBall(float currentX, float currentY, float currentZ, float gameUpdateMs) {
        return imageData.moveBall(currentY, currentX, currentZ, gameUpdateMs);
    }
}
