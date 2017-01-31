package rosko.bojan.rupko.imageview;

import android.graphics.RectF;

import java.io.Serializable;

/**
 * Created by rols on 31.1.17..
 */

public class MyRectF extends RectF implements Serializable {


    public MyRectF(float left, float top, float right, float bottom) {
        super(left,top,right,bottom);
    }

    public MyRectF() {
        super();
    }

}
