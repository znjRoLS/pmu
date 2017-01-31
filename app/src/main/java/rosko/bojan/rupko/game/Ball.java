package rosko.bojan.rupko.game;

import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;

/**
 * Created by rols on 31.1.17..
 */

public class Ball {
    MyPointF center;
    float radius;

    ImageData imageData;

    public Ball(ImageData imageData) {
        this.imageData = imageData;
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


}
