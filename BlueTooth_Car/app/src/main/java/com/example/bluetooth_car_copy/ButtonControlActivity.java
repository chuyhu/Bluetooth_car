package com.example.bluetooth_car_copy;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;

public class ButtonControlActivity extends Activity implements View.OnTouchListener {

    private Button upButton,downButton,leftButton,rightButton;
    private OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        upButton = (Button)findViewById(R.id.upButton);
        downButton = (Button)findViewById(R.id.downButton);
        leftButton = (Button)findViewById(R.id.leftButton);
        rightButton = (Button)findViewById(R.id.rightButton);

        upButton.setOnTouchListener(this);
        downButton.setOnTouchListener(this);
        leftButton.setOnTouchListener(this);
        rightButton.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.upButton:
                writeOutputStream(event,"a","e");
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    upButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.up1));
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    upButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
                }
                break;
            case R.id.downButton:
                writeOutputStream(event,"b","e");
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    downButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.down1));
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    downButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
                }
                break;
            case R.id.leftButton:
                writeOutputStream(event,"c","e");
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    leftButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.left1));
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    leftButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.left));
                }
                break;
            case R.id.rightButton:
                writeOutputStream(event,"d","e");
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    rightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.right1));
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    rightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
                }
                break;
        }
        return false;
    }

    private void writeOutputStream(MotionEvent event, String a, String e) {
        //每次点击都要重新获取输出流，以防止蓝牙断开重新连接后输出流改变了
        outputStream = MainActivity.outputStream;
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            String message;
            byte[] buffer;
            message = a;
            buffer = message.getBytes();
            try{
                outputStream.write(buffer);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            String message;
            byte[] buffer;
            message = e;
            buffer = message.getBytes();
            try{
                outputStream.write(buffer);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
