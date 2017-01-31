package rosko.bojan.rupko.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

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
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyImageView;
import rosko.bojan.rupko.newlevel.NewLevelImageData;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.statistics.StatsDbHelper;

/**
 * Created by rols on 1/12/17.
 */

public class GameController implements SensorEventListener {

    Context context;

    SensorManager sensorManager;
    Sensor accelerometerSensor;

    ImageData imageData;
    MyImageView myImageView;
    final float MAGNITUDE = 5.0f;

    StatsDbHelper dbHelper;
    String levelName;

    private GameImageData gameImageData;


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sensorChanged(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface ViewInterface {
        void updateView();
    }

    protected ViewInterface view;

    public void startLevel() {

    }

    public GameController(Context context, ViewInterface view, MyImageView myImageView, String levelName) {
        this.view = view;
        this.myImageView = myImageView;
        this.context = context;
        this.levelName = levelName;

        imageData = myImageView.getImageData();

        gameImageData = (GameImageData) imageData;

        setupGameVectorSensor();

        dbHelper = new StatsDbHelper(context);
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

        float dx = -values[0];
        float dy = values[1];
        float dz = values[2];

        double xTheta = Math.atan2(dx, dz);
        double yTheta = Math.atan2(dy, dz);

        gameImageData.moveBall((float)xTheta * MAGNITUDE, (float)yTheta * MAGNITUDE);
        myImageView.invalidate();
        view.updateView();
    }

    public void onResume() {
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    public void gameEnd() {

    }

    public void writeScore(String username, Time time) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper.insertNewStat(levelName, username, time);
    }


    public boolean loadLevel(String filename) {

        Level level = null;

        String levelSuffix = GameConfiguration.currentConfiguration.LEVEL_SUFIX;
        File internalDir = context.getFilesDir();
        File file = new File(internalDir, filename + levelSuffix);
        boolean fileExisted = file.exists();

        if (!fileExisted) {
            Logger.throwError(context, "Level doesn't exist! Level: " + filename + levelSuffix);
            return false;
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

        return true;
    }

}
