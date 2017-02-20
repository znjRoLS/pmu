package rosko.bojan.rupko.imageview;

import android.content.Context;
import android.util.Pair;

import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyImageView;

/**
 * Created by rols on 1/19/17.
 */

public class Controller {

    protected Context context;
    protected ViewInterface view;
    protected MyImageView myImageView;
    protected ImageData imageData;

    public interface ViewInterface {
        void updateImageView();
    }

    public Controller(Context context, ViewInterface view, final MyImageView myImageView) {
        this.context = context;
        this.view = view;
        this.myImageView = myImageView;

        imageData = myImageView.getImageData();


    }

    public void setImageDate(ImageData imageData) {
        this.imageData = imageData;
    }

    public MyImageView getMyImageView() {
        return myImageView;
    }
}
