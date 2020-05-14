13
https://raw.githubusercontent.com/wix-incubator/rn-contact-tracing/master/lib/android/src/main/java/com/wix/specialble/sensor/AccelerometerManager.java
package com.wix.specialble.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

import com.wix.specialble.listeners.ISensorListener;

public class AccelerometerManager implements ISensorListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static final String TAG = AccelerometerManager.class.getSimpleName();
    private SensorEvent mSensorEvent;
    private boolean mIsSensorAvailable;

    public AccelerometerManager(SensorManager sensorManager) {
        mSensorManager = sensorManager;
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mIsSensorAvailable = true;
        }
    }

    public void registerListener() {
        if (mIsSensorAvailable) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListener() {
        try {
            mSensorManager.unregisterListener(this);
        } catch (Exception e) {
            Log.e(TAG, "unregisterListener: ", e);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSensorEvent = event;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public boolean isSensorAvailable() {
        return mIsSensorAvailable;
    }

    public float[] getEvents() {
        return mSensorEvent == null ? new float[6] : mSensorEvent.values;
    }
}
