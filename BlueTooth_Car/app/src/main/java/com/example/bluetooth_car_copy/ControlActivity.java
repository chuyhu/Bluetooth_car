package com.example.bluetooth_car_copy;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;

public class ControlActivity extends AppCompatActivity {
    private OutputStream outputStream;
    private Sensor sensor;
    private SensorManager sensorManager;
    private boolean upOneTime = true;
    private boolean downOneTime = true;
    private boolean leftOneTime = true;
    private boolean rightOneTime = true;
    private boolean stopOneTime = true;

    //屏幕常亮
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;

    private Button button1,button2,button3,button4;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        powerManager = (PowerManager)this.getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"My Lock");

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        buttonInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
        sensorManager.registerListener(eventListener,sensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
        sensorManager.unregisterListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        try{
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    private SensorEventListener eventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                if (event.values[0] < -2.4 && upOneTime){
                    writeOutputStream("a");
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.left));
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.right1));
                    upOneTime = false;
                    downOneTime = true;
                    leftOneTime = true;
                    rightOneTime = true;
                    stopOneTime = true;
                }
                if (event.values[0] > 2.4 && downOneTime){
                    writeOutputStream("b");
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.left1));
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
                    upOneTime = true;
                    downOneTime = false;
                    leftOneTime = true;
                    rightOneTime = true;
                    stopOneTime = true;
                }
                if (event.values[1] < -2.2 && leftOneTime){
                    writeOutputStream("c");
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.up1));
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.left));
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
                    upOneTime = true;
                    downOneTime = true;
                    leftOneTime = false;
                    rightOneTime = true;
                    stopOneTime = true;
                }
                if (event.values[1] > 2.2 && rightOneTime){
                    writeOutputStream("d");
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.down1));
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.left));
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
                    upOneTime = true;
                    downOneTime = true;
                    leftOneTime = true;
                    rightOneTime = false;
                    stopOneTime = true;
                }
                if ((-2.4 < event.values[0] && event.values[0] < 2.4) && (-2.2 < event.values[1] && event.values[1]<2.2)&& stopOneTime){
                    writeOutputStream("e");
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.left));
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
                    upOneTime = true;
                    downOneTime = true;
                    leftOneTime = true;
                    rightOneTime = true;
                    stopOneTime = false;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void writeOutputStream(String a) {
        outputStream = MainActivity.outputStream;
        String message;
        byte[] buffer;
        message = a;
        buffer = message.getBytes();
        try{
            outputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buttonInit() {
        button1 = (Button)findViewById(R.id.upButton);
        button2 = (Button)findViewById(R.id.downButton);
        button3 = (Button)findViewById(R.id.leftButton);
        button4 = (Button)findViewById(R.id.rightButton);
        button1.setText("左");
        button2.setText("右");
        button3.setText("下");
        button4.setText("上");
    }
}
