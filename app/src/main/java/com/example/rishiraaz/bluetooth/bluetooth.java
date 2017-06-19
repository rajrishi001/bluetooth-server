package com.example.rishiraaz.bluetooth;

import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup ;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.IntentFilter;
import android.view.View.OnClickListener;

import android.os.Handler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
//import java.util.logging.Handler;
import java.lang.Object;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
import android.os.Message;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;
import java.lang.Math;



public class bluetooth extends AppCompatActivity implements SensorEventListener {
    private static final String Tag = "bluetooth";
    BluetoothAdapter mBluetoothAdapter;
    int Bluetooth_REQUEST = 1;
    UUID uuid = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");


   private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;
    private int mdeg=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Button btonoff = (Button) findViewById(R.id.btonoff);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        btonoff.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                                           if (mBluetoothAdapter == null) {
                                               Toast.makeText(getBaseContext(), "No Bluetooth found", Toast.LENGTH_LONG).show();
                                           } else {
                                               if (!mBluetoothAdapter.isEnabled()) {
                                                   Intent enablebtintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                                   startActivityForResult(enablebtintent, Bluetooth_REQUEST);
                                               } else {
                                                   AcceptThread bt = new AcceptThread();
                                                   bt.start();
                                                   Toast.makeText(getBaseContext(), "Ble connection started", Toast.LENGTH_LONG).show();
                                               }
                                           }

                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
                startActivity(discoverableIntent);
                                           /*int i=0;
                                           while(i<10){
                                               rssi.setText("direction"+dew.mdeg);
                                               i++;
                                               SystemClock.sleep(1000);
                                           }*/
                                       //       Toast.makeText(getApplicationContext(), "Now your device is discoverable by others", Toast.LENGTH_LONG).show();

////                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//                if (pairedDevices.size() > 0) {
//                    // There are paired devices. Get the name and address of each paired device.
//                    for (BluetoothDevice device : pairedDevices) {
//                        String deviceName = device.getName();
//                        String deviceHardwareAddress = device.getAddress(); // MAC address
//                        Log.v("device1", "Device name: " + deviceName + " device mac: " + deviceHardwareAddress);
//                    }
//                }
//                for(int i=0; i<2; i++){
//                   Log.v("BLuetoot","In");
//                    mBluetoothAdapter.startDiscovery();
//                    SystemClock.sleep(13000);}
                /*class bluetoothcheck extends Thread{
                    public void run()
                    {
                        while(true)
                        {
//                            rssi.getText().clear();
                            mBluetoothAdapter.startDiscovery();
                            SystemClock.sleep(3000);
                        }

                    }
                };

                bluetoothcheck btl = new bluetoothcheck();
                btl.start();*/
                                   }}

        );

    }

    public void onActivityResult(int rq_code, int rs_code, Intent data) {
        if (rq_code == Bluetooth_REQUEST) {
            if (rs_code == RESULT_OK) {
                Toast.makeText(getBaseContext(), "Ble is on", Toast.LENGTH_LONG).show();
                AcceptThread bt = new AcceptThread();
                bt.start();
                Toast.makeText(getBaseContext(), "Ble connection started", Toast.LENGTH_LONG).show();
            } else if (rs_code == RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "Ble can't be on", Toast.LENGTH_LONG).show();
            }
        }

    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bluetooth, menu);
        return true;
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("AAAAAAAAA","Function called");
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi1 = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                TextView rssi = (TextView) findViewById(R.id.rssi);
                rssi.setText(rssi.getText() + name + " => " + rssi1 + "dBm\n");
            }
        }
    };*/


    //UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");


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
    //int j=0;
    private  BluetoothSocket socket, socket1;
    private BluetoothServerSocket mmServerSocket;
    private class AcceptThread extends Thread {


        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket
// because mmServerSocket is final.
            BluetoothServerSocket tmp = null;
            try {
                Log.v("raj", "wow");
                // MY_UUID is the app's UUID string, also used by the client code.
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("GiONEE P5 mini", uuid);
                Log.v("raj", "wow1");
            } catch (IOException e) {
                Log.e("raj", "Socket's listen() method failed", e);
            }
            mmServerSocket = tmp;
        }


        public void run() {
            socket = null;
            //mBluetoothAdapter.cancelDiscovery();
            // Keep listening until exception occurs or a socket is returned.
            while (true) {
                try {
                    Log.v("raj", "wow2");
                    socket = mmServerSocket.accept();
                    Log.v("raj", "wow3");
                } catch (IOException e) {
                    Log.e("raj", "Socket's accept() method failed", e);
                    break;
                }
                if (socket != null) {
                    // A connection was accepted. Perform work associated with.
                    // the connection in a separate thread.
                    // manageMyConnectedSocket(socket);
                    Log.v("raj", "wow5");
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Ble connected", Toast.LENGTH_LONG).show();
                        }
                    });
                    //int j = 0;

                    socket1=socket;
                    mdeg = 1;

                    /*try {
                        mmServerSocket.close();
                        Log.v("raj", "socket close");
                    } catch (IOException e) {
                        Log.e("raj", "Could not close the connect socket", e);
                    }*/
                    Log.v("raj", "break;");
                    break;
                }
            }
        }


        // Closes the connect socket and causes the thread to finish.
        /*public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e("raj", "Could not close the connect socket", e);
            }

        }*/

    }

    public int j=0;
        public void onSensorChanged(SensorEvent event) {
            final float alpha1 = 0.97f;
            final float alpha = 0.97f;
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
            if (mLastMagnetometerSet && mLastAccelerometerSet) {
                SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
                SensorManager.getOrientation(mR, mOrientation);
                float azimuthInRadians = mOrientation[0];
                float azimuthInDegress = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;
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
                j=j+1;
                if(j>200 && mdeg==1){
                    final ConnectedThread2 raw=new ConnectedThread2(socket1);
                    String message = Float.toString(mCurrentDegree);// dew.mdeg;
                    raw.write(message.getBytes());
               Log.v("raj", Integer.toString(Math.round(mCurrentDegree)));
                    j=0;}


            }
        }


        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }


   private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;
    }
    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MessageConstants.MESSAGE_READ:
                    byte[] readBuffer = (byte[]) msg.obj;
                    String readMessage = new String(readBuffer, 0, msg.arg1);
                    //arrayAdapterMsg.add(btDevice.getName() + ": " + readMessage);
                    Toast.makeText(getBaseContext(), readMessage, Toast.LENGTH_LONG).show();

                    break;

                case MessageConstants.MESSAGE_WRITE:
                    byte[] writeBuffer = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuffer);
                    TextView rssi = (TextView) findViewById(R.id.rssi);
                    rssi.setText("Sent" + writeMessage);
                    //arrayAdapterMsg.add("Me: " + "test");
                    //Toast.makeText(getBaseContext(), writeMessage, Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };
    private class ConnectedThread1 extends Thread {
        private Handler mHandler;
        private final InputStream mmInStream;
        //private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread1(BluetoothSocket socket) {
//mmSocket = socket;
            InputStream tmpIn = null;
//OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
// member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("raj", "Error occurred when creating input stream", e);
            }
 /*try {
 tmpOut = socket.getOutputStream();
 } catch (IOException e) {
 Log.e(TAG, "Error occurred when creating output stream", e);
 }*/

           mmInStream = tmpIn;
// mmOutStream = tmpOut;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.
                    Message readMsg = mHandler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1, mmBuffer);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                    Log.d("raj", "Input stream was disconnected", e);
                    break;
                }
            }
        }

    }

    private class ConnectedThread2 extends Thread {
       // private Handler mHandler;
        //private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread2(BluetoothSocket socket) {
//mmSocket = socket;
            //InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
// member streams are final.
            /*try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("raj", "Error occurred when creating input stream", e);
            }*/
           try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("raj", "Error occurred when creating output stream", e);
            }

            //mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                // Share the sent message with the UI activity.
                Message writtenMsg = mHandler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, bytes);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e("raj", "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast", "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                mHandler.sendMessage(writeErrorMsg);
            }
        }

    }
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e("raj", "Could not close the connect socket", e);
        }

    }
    public void onDestroy(){
        super.onDestroy();
        try {
            mmServerSocket.close();
            Log.v("raj", "socket close");
        } catch (IOException e) {
            Log.e("raj", "Could not close the connect socket", e);
        }
    }

}