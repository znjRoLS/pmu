package rosko.bojan.rupko.game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;


/**
 * Created by rols on 2/3/17.
 */

public class Timer extends TextView{

    //todo offscreen countdown and return to start

    private long startTimeMillis;
    private long lastTimeMillis;
    private long stepMillis;


    public Timer(Context context) {
        super(context);
    }

    public Timer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Timer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Timer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getFormatedTime() {
        long diffTime = lastTimeMillis - startTimeMillis;

        long minutes = diffTime / 60000;
        long seconds = diffTime / 1000 % 60;
        long millis = diffTime % 1000;

        return String.format(Locale.getDefault(), "%02d:%02d:%03d", minutes, seconds, millis);
    }

    public void updateView() {
        post(new Runnable() {
            @Override
            public void run() {
                setText(getFormatedTime());
            }
        });
    }


    //returns number of milliseconds that one should sleep so that the time from last tick is equal to stepMillis
    public long tick(){
        lastTimeMillis += stepMillis;
        return lastTimeMillis - System.currentTimeMillis();
    }

    public void start(float frequency) {
        stepMillis = (long)((frequency + 999) / frequency); //ceil(1000/freq)
        startTimeMillis = lastTimeMillis = System.currentTimeMillis();
    }

    public void reset() {
        startTimeMillis = lastTimeMillis = System.currentTimeMillis();
    }

    public long getTime() {
        return lastTimeMillis - startTimeMillis;
    }
}
