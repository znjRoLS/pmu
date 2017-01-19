package rosko.bojan.rupko.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by rols on 1/15/17.
 */

public class MyImageView extends ImageView {

    ImageData imageData;
    Paint paint;

    public ImageData getImageData() {
        return imageData;
    }

    public void MyImageViewConstructor() {
        imageData = new ImageData();
        paint = new Paint();
    }

    public MyImageView(Context context) {
        super(context);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        MyImageViewConstructor();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        MyImageViewConstructor();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(imageData.getCircleCenter().x, imageData.getCircleCenter().y, 15, paint);
    }
}
