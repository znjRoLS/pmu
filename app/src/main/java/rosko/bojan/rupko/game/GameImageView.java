package rosko.bojan.rupko.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.newlevel.NewLevelImageData;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/22/17.
 */

public class GameImageView extends MyImageView {

    GameImageData gameImageData;

    Paint ballPaint;

    private void MyGameImageViewContructor() {
        imageData = new GameImageData();

        gameImageData = (GameImageData) imageData;

        ballPaint = new Paint();
        ballPaint.setColor(GameConfiguration.currentConfiguration.BALL_COLOR);
    }

    public GameImageView(Context context) {
        super(context);
        MyGameImageViewContructor();
    }

    public GameImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyGameImageViewContructor();
    }

    public GameImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        MyGameImageViewContructor();
    }

    public GameImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        MyGameImageViewContructor();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Hole ball = gameImageData.getBall();

        canvas.drawCircle(ball.getCenter().x, ball.getCenter().y, ball.getRadius(), ballPaint);
    }
}
