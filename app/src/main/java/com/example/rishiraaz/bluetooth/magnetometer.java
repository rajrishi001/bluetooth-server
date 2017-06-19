package com.example.rishiraaz.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.Math;

public class magnetometer extends Activity implements SensorEventListener {


    public int mdeg;

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    public float mCurrentDegree = 0f;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
    }

    public void onSensorChanged(SensorEvent event) {
        final float alpha1=0.97f;
        final float alpha=0.97f;
        if (event.sensor == mAccelerometer) {
            //System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
            mLastAccelerometer[0] = alpha * mLastAccelerometer[0] + (1 - alpha)
                    * event.values[0];
            mLastAccelerometer[1] = alpha * mLastAccelerometer[1] + (1 - alpha)
                    * event.values[1];
            mLastAccelerometer[2] = alpha * mLastAccelerometer[2] + (1 - alpha)
                    * event.values[2];

        }
        if (event.sensor == mMagnetometer) {
            //System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
            mLastMagnetometer[0] = alpha1 * mLastMagnetometer[0] + (1 - alpha1)
                    * event.values[0];
            mLastMagnetometer[1] = alpha1 * mLastMagnetometer[1] + (1 - alpha1)
                    * event.values[1];
            mLastMagnetometer[2] = alpha1 * mLastMagnetometer[2] + (1 - alpha1)
                    * event.values[2];
        }
        if (mLastMagnetometerSet || mLastAccelerometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            /*RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(250);

            ra.setFillAfter(true);

            mPointer.startAnimation(ra);*/
            mCurrentDegree = azimuthInDegress;
            mdeg=Math.round(mCurrentDegree);

        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

}
