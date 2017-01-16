package rosko.bojan.rupko;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements Controller.ViewInterface, SensorEventListener {

    Controller controller;
    SensorManager sensorManager;
    Sensor gameVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyImageView myImageView = (MyImageView) findViewById(R.id.myImageView);
        controller = new Controller(this, myImageView);

        setupGameVectorSensor();
    }

    private void setupGameVectorSensor() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gameVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener(this, gameVectorSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void updateView() {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        controller.sensorChanged(event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gameVectorSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
