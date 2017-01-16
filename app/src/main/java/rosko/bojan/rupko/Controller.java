package rosko.bojan.rupko;

import android.util.Log;

/**
 * Created by rols on 1/12/17.
 */

public class Controller {

    ImageData imageData;
    MyImageView myImageView;
    final float MAGNITUDE = 5.0f;

    public interface ViewInterface {
        void updateView();
    }

    protected ViewInterface view;

    public Controller(ViewInterface view, MyImageView myImageView) {
        this.view = view;
        this.myImageView = myImageView;

        imageData = myImageView.getImageData();
    }

    public void sensorChanged(float values[]) {
        for (int i = 0 ; i< values.length; i ++) {
//            Log.d("sensor change ", "i " + i + " value " + values[i]);
        }
//        Log.d("sensor change ", " value " + values[1]);

        float dx = -values[0];
        float dy = values[1];
        float dz = values[2];

        double xTheta = Math.atan2(dx, dz);
        double yTheta = Math.atan2(dy, dz);

        imageData.incCircle((float)xTheta * MAGNITUDE, (float)yTheta * MAGNITUDE);
        myImageView.invalidate();
        view.updateView();
    }

}
