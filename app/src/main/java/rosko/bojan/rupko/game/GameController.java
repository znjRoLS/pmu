package rosko.bojan.rupko.game;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.Time;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.newlevel.NewLevelImageData;
import rosko.bojan.rupko.newlevel.SaveDialog;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.statistics.StatsDbHelper;

/**
 * Created by rols on 1/12/17.
 */

public class GameController implements SensorEventListener {

    private float GAME_UPDATE_MS;



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
        }
    }

    //TODO:add restart game

    Timer timer;

    Context context;

    SensorManager sensorManager;
    Sensor accelerometerSensor;
    private float currentXTheta;
    private float currentYTheta;
    private LowPassFilter filterX, filterY, filterZ;
    private float currentX, currentY, currentZ;
    private float pixelsByMetersRatio;

    ImageData imageData;
    MyImageView myImageView;

    StatsDbHelper dbHelper;
    String levelName;

    GameAbstractActivity gameActivity;

    private GameImageData gameImageData;

    private boolean gameEnd;


    public void setPixelsRatio(float pixelsByMetersRatio) {
        this.pixelsByMetersRatio = pixelsByMetersRatio;
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

    public GameController(Context context, ViewInterface view, MyImageView myImageView, String levelName) {
        this.view = view;
        this.myImageView = myImageView;
        this.context = context;
        this.levelName = levelName;
        this.gameActivity = (GameAbstractActivity) context;

        filterX = new LowPassFilter();
        filterY = new LowPassFilter();
        filterZ = new LowPassFilter();

        //todo : fix this
        timer = (Timer)((GameAbstractActivity)context).findViewById(R.id.timerTextView);

        GAME_UPDATE_MS = (long)((999 + GameConfiguration.currentConfiguration.GAME_UPDATE_RATE) / GameConfiguration.currentConfiguration.GAME_UPDATE_RATE);

        currentXTheta = 0;
        currentYTheta = 0;

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

        Log.d("sensor", "wtf?");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void sensorChanged(float values[]) {
        for (int i = 0 ; i< values.length; i ++) {
//            Log.d("sensor change ", "i " + i + " value " + values[i]);
        }

        // Log.d("sensor change ", " value " + values[1]);

        float dx = values[0];
        float dy = values[1];
        float dz = values[2];

//        Log.d("sensor", dx + "dx");
//        Log.d("sensor", dy + "dy");
//        Log.d("sensor", dz + "dz");

        // this is already normalized dumbass
//        currentXTheta = (float)Math.atan2(dx, dz);
//        currentYTheta = (float)Math.atan2(dy, dz);



        currentX = filterX.filter(dx);
        currentY = filterY.filter(dy);
        currentZ = filterZ.filter(dz);
    }

    public void onResume() {
        Log.d("sensor", "register1");
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause() {

        Log.d("sensor", "unregisterd1");
        sensorManager.unregisterListener(this);
        gameEnd = true;
    }

    public void gameStop() {
        Log.d("sensor", "unregisterd2");
        sensorManager.unregisterListener(this);
        gameEnd = true;
    }

    public void gameEnd() {

        gameEnd = true;

        view.showEndGameDialog();
    }


    public void loadLevel() {
        Level level = null;

        String levelSuffix = GameConfiguration.currentConfiguration.LEVEL_SUFIX;
        File internalDir = context.getFilesDir();
        File file = new File(internalDir, levelName + levelSuffix);
        boolean fileExisted = file.exists();

        if (!fileExisted) {
            Logger.throwError(context, "Level doesn't exist! Level: " + levelName + levelSuffix);
            return;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            level = (Level) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Logger.throwError(context, "Problem with output stream!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.throwError(context, "Problem with class not found!");
            e.printStackTrace();
        }

        Logger.throwError(context, "Successfully loaded level!");

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
