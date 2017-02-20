package rosko.bojan.rupko.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import rosko.bojan.rupko.imageview.Level;
import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.statistics.StatsDbHelper;

/**
 * Created by rols on 1/12/17.
 */

public class GameController implements SensorEventListener {

    //TODO:add restart game
    private float GAME_UPDATE_MS;

    private MediaPlayer bounceMediaPlayer;
    private Timer timer;
    private Context context;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private LowPassFilter filterX, filterY, filterZ;
    private float currentX, currentY, currentZ;
    private float pixelsByMetersRatio;

    private String levelName;
    private ImageData imageData;
    private MyImageView myImageView;
    private GameAbstractActivity gameActivity;
    private GameImageData gameImageData;

    private boolean gameEnd;

    private long lastBounceTime = 0;
    private static final int BOUNCE_WAIT_TIME = 50;

    private class bounceSoundTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                bounceMediaPlayer.stop();
                bounceMediaPlayer.prepare();
                bounceMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    Thread timerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            timer.start(GameConfiguration.currentConfiguration.GAME_UPDATE_RATE);
            while(!gameEnd) {
                long deltaTime;
                try {
                    Ball.BallMovement ballState = gameActivity.moveBall(currentX, currentY, currentZ, GAME_UPDATE_MS/1000f);

                    view.updateView();

                    processGameState(ballState);
                    deltaTime = timer.tick();
                    timer.updateView();
                    if (deltaTime > 0) {
                        Thread.sleep(deltaTime);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private void processGameState(Ball.BallMovement ballState) {

        switch (ballState) {
            case START:
                //TODO: also, velocity should be small or sth like that
                timer.reset();
                break;
            case END:
                gameEnd();
                break;
            case HOLE:
                restartLevel();
                break;
            case BOUNCE:
                if (timer.getTime() - lastBounceTime > BOUNCE_WAIT_TIME) {
                    (new bounceSoundTask()).execute("");
                }
                lastBounceTime = timer.getTime();

                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sensorChanged(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface ViewInterface {
        void updateView();
        void showEndGameDialog();
    }

    protected ViewInterface view;

    private void setPixelsByMetersRatio(GameAbstractActivity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = dm.heightPixels/dm.xdpi;
        double y = dm.widthPixels/dm.ydpi;
        x *= 2.54;
        y *= 2.54;

        pixelsByMetersRatio = dm.heightPixels / (float)(x/100);
    }

    public GameController(GameAbstractActivity context, ViewInterface view, MyImageView myImageView, String levelName) {
        this.view = view;
        this.myImageView = myImageView;
        this.context = context;
        this.levelName = levelName;
        this.gameActivity = (GameAbstractActivity) context;

        setPixelsByMetersRatio(context);

        bounceMediaPlayer = MediaPlayer.create(context,
                GameConfiguration.currentConfiguration.AUDIO_BALL_BOUNCE);

        filterX = new LowPassFilter();
        filterY = new LowPassFilter();
        filterZ = new LowPassFilter();

        timer = (Timer)((GameAbstractActivity)context).findViewById(R.id.timerTextView);

        GAME_UPDATE_MS = (long)((999 + GameConfiguration.currentConfiguration.GAME_UPDATE_RATE) / GameConfiguration.currentConfiguration.GAME_UPDATE_RATE);

        imageData = myImageView.getImageData();
        gameImageData = (GameImageData) imageData;

        setupGameVectorSensor();

        final GameController that = this;
        //update sizes
        myImageView.post(new Runnable() {
            @Override
            public void run() {
                imageData.setScreenSize(new Pair<Integer, Integer>(that.myImageView.getHeight(), that.myImageView.getWidth()));
                imageData.updateRadius();
            }
        });
    }

    public Timer getTimer() {
        return timer;
    }

    private void setupGameVectorSensor() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void sensorChanged(float values[]) {
        float dx = values[0];
        float dy = values[1];
        float dz = values[2];

        currentX = filterX.filter(dx);
        currentY = filterY.filter(dy);
        currentZ = filterZ.filter(dz);
    }

    public void onResume() {
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
        gameEnd = true;
    }

    public void gameStop() {
        sensorManager.unregisterListener(this);
        gameEnd = true;
    }

    public void gameEnd() {
        gameEnd = true;
        view.showEndGameDialog();
    }


    public void loadLevel() {
        Level level = Level.loadLevel(context, levelName);

        gameImageData.loadLevel(level);
        gameImageData.updateRadius();
        gameImageData.setPixelsRatio(pixelsByMetersRatio);
    }

    public void startLevel() {
        gameEnd = false;
        loadLevel();
        timerThread.start();
        restartLevel();
    }

    public void restartLevel() {
        timer.reset();
        filterX.reset();
        filterY.reset();
        filterZ.reset();
        gameImageData.resetBall();
    }
}
