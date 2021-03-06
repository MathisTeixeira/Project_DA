package com.teixeira.mathis.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Game_Activity extends Activity implements SensorEventListener {

    private GameView gameView;

    private SensorManager sensorManager;
    private Sensor sensor;
    private int angle = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create the GameView and set it as the content view
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialize the orientation sensor
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    // Register sensor on start
    public void onStart(){
        super.onStart();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    // Unregister sensor on pause
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // Register sensor on resume
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    // Reorganize sensor's data in a matrix
    // Function executes every time the sensor changes
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[16];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

        float[] remappedRotationMatrix = new float[16];
        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix);

        float[] orientations = new float[3];
        SensorManager.getOrientation(remappedRotationMatrix, orientations);

        for(int i = 0; i < 3; i++) {
            orientations[i] = (float)(Math.toDegrees(orientations[i]));
        }

        // Move on the horizontal axis
        if(orientations[0] > angle) {
            gameView.moveLeft();
        } else if(orientations[0] < -angle) {
            gameView.moveRight();
        }

        // Move on the vertical axis
        if(orientations[1] > angle) {
            gameView.moveDown();
        } else if(orientations[1] < -angle) {
            gameView.moveUp();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}