package com.blueweidy.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class Fragment4 extends Fragment implements View.OnClickListener{

    SensorManager sensorManager;
    Sensor accelerometerSensor;
    boolean accelerometerPresent;

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;

    Button startBttn;
    Chronometer timerText;
    TextView test;

    private Animation fate;

    long pauseSet;
    boolean isFocusing = false;
    boolean deviceFacingDown;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flag4_layout, container, false);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList.size() > 0){
            accelerometerPresent = true;
            accelerometerSensor = sensorList.get(0);
        }else {
            accelerometerPresent = false;
            Toast.makeText(getActivity(), "No accalerometer present!!", Toast.LENGTH_SHORT).show();
        }

        startBttn = view.findViewById(R.id.focus_mode_bttn);
        startBttn.setOnClickListener(this);

        test = view.findViewById(R.id.testorienty);
        timerText = view.findViewById(R.id.textView_timer);
        fate = AnimationUtils.loadAnimation(getActivity(), R.anim.disapear);

        //region test translation/rotation
        /*
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                orienTextx.setText("X:" + tx);
                orienTexty.setText("Y:" + ty);
                orienTextz.setText("Z:" + tz);
            }
        });
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                orienTextx.setText("X:" + rx);
                orienTexty.setText("Y:" + ry);
                orienTextz.setText("Z:" + rz);
            }
        });
        */
//endregion

        return view;
    }

    private void remindDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.focusmodedialog);
        dialog.show();
    }

    private void startTimer(){
        if (!isFocusing){
            timerText.setBase(SystemClock.elapsedRealtime() - pauseSet);
            timerText.start();
            isFocusing = true;
            startBttn.setVisibility(View.INVISIBLE);
        }
    }

    private void pauseTimer(){
        if (isFocusing){
            timerText.stop();
            pauseSet = SystemClock.elapsedRealtime() - timerText.getBase();
            isFocusing = false;
        }
    }

    private void resetTimer(){
        timerText.setBase(SystemClock.elapsedRealtime());
        pauseSet = 0;
    }

    private SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent arg0) {
            float z_value = arg0.values[2];
            if (z_value >= 0){
                deviceFacingDown = false;
                test.setText("On");
            }else {
                deviceFacingDown = true;
                test.setText("Off");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



    private void checkDeviceState(){
        if (deviceFacingDown){
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (deviceFacingDown){

                    }
                }
            },2000);
        }
    }

    private void unlockScreen(){
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    public void turnOnScreen(){
        test.setText("On");
        on();
    }

    public void turnOffScreen(){
        test.setText("Off");
        off();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void on(){
        Log.v("ProximityActivity", "ON!");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void off(){
        Log.v("ProximityActivity", "OFF!");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "tag");
        mWakeLock.acquire();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometerPresent){
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (accelerometerPresent){
            sensorManager.unregisterListener(accelerometerListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.focus_mode_bttn:{
                //remindDialog();
                off();
            }
        }
    }
}
