package rosko.bojan.rupko.imageview;

import java.io.Serializable;

/**
 * Created by rols on 1/22/17.
 */

public class MyPointF implements Serializable {

    private static final long serialVersionUID = 555555L;

    public float x;
    public float y;

    public MyPointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MyPointF() {
        x = y = 0;
    }
}
