package com.teixeira.mathis.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textView;
    private float xAcc, yAcc, zAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView2);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    public void onStart(){
        super.onStart();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void startGame(View view){
        Intent intent = new Intent(this, Game_Activity.class);
        startActivity(intent);
    }

    public void leaderboard(View view){
        Intent intent = new Intent(this, Leaderboard_Activity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR){
            xAcc = event.values[0];
            yAcc = event.values[1];
            zAcc = event.values[2];
            textView.setText("x: "+xAcc+"\ny: "+yAcc+"\nz: "+zAcc);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}