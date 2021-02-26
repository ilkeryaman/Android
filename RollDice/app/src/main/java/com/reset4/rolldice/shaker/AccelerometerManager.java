package com.reset4.rolldice.shaker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.reset4.rolldice.DiceActivity;

/**
 * Created by eilkyam on 28.05.2015.
 */
public class AccelerometerManager {
    private float shakeThreshold = 0;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;
    private float lastX, lastY, lastZ;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private DiceActivity diceActivity;

    public AccelerometerManager(DiceActivity activity)
    {
        diceActivity = activity;
        initializeSensor();
    }

    private void initializeSensor() {
        sensorManager = (SensorManager) diceActivity.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(diceActivity, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            shakeThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // fai! we dont have an accelerometer!
        }
    }

    public void onSensorChange(SensorEvent event)
    {
        // get the change of the x,y,z values of the accelerometer
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        EditionControl.shake(diceActivity, deltaX, deltaY, deltaZ, shakeThreshold);

        lastX = event.values[0];
        lastY = event.values[1];
        lastZ = event.values[2];

    }

    public void resume()
    {
        sensorManager.registerListener(diceActivity, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause()
    {
        sensorManager.unregisterListener(diceActivity);
    }

    public void stop()
    {
        sensorManager.unregisterListener(diceActivity);
    }
}
