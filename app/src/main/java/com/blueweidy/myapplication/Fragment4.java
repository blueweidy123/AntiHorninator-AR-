package com.blueweidy.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.w3c.dom.Text;

import java.util.List;

public class Fragment4 extends Fragment implements View.OnClickListener{

    SensorManager sensorManager;
    Sensor accelerometerSensor;
    boolean accelerometerPresent;

    Button startBttn;
    TextView timerText;
    TextView test;

    static final int RESULT_ENABLE = 1;
    DevicePolicyManager devicePolicyManager;
    ComponentName componentName;

    boolean isFocusing = false;
    boolean deviceFacingDown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flag4_layout, container, false);

        startBttn = view.findViewById(R.id.focus_mode_bttn);

        devicePolicyManager = (DevicePolicyManager)getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(getActivity(), Controller.class);

//region sensor init
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList.size() > 0){
            accelerometerPresent = true;
            accelerometerSensor = sensorList.get(0);
        }else {
            accelerometerPresent = false;
            Toast.makeText(getActivity(), "No accalerometer present!!", Toast.LENGTH_SHORT).show();
        }
//endregion

        startBttn.setOnClickListener(this);

        boolean active = devicePolicyManager.isAdminActive(componentName);
        if (active){
            startBttn.setText("START");
        }else {
            startBttn.setText("Enable");
        }

        startBttn.setOnClickListener(this);

        test = view.findViewById(R.id.testorienty);
        timerText = view.findViewById(R.id.textView_timer);

        setTimerText();

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

//region Sensor listener
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
            if (deviceFacingDown){
                if (isFocusing){
                    startTimer();
                    turnScreenOff();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
//endregion

//region lock/unlock screen method

    private void unlockScreen(){
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }


    public void turnScreenOff(){
        devicePolicyManager.lockNow();
    }
//endregion

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

    public void startTimer(){
        Intent intent = new Intent(getActivity(), BroadcastService.class);
        getActivity().startService(intent);
    }

    public void stopTimer(){
        Intent intent = new Intent(getActivity(), BroadcastService.class);
        getActivity().stopService(intent);
    }

    public void setTimerText(){
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String time = intent.getStringExtra(BroadcastService.TIME);
                        timerText.setText(time);
                    }
                }, new IntentFilter(BroadcastService.ACTION_TIME_BROADCAST)
        );
    }

    public void onStopTimer(){
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String time = intent.getStringExtra(BroadcastService.STOP_TIME);
                        timerText.setText(time);
                        Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.focus_end_dialog);
                        final TextView focusEnd = dialog.findViewById(R.id.focus_mode_ending_text);
                        focusEnd.setText("You just stay focused only for " + time + "?? papa disapointed about you !!!");
                        dialog.show();
                    }
                }, new IntentFilter(BroadcastService.ACTION_TIME_END_BROADCAST)
        );
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //start button
            case R.id.focus_mode_bttn:{
                boolean active = devicePolicyManager.isAdminActive(componentName);
                if (active) {
                    if (isFocusing){
                        startBttn.setText("START");
                        onStopTimer();
                        stopTimer();
                        isFocusing = false;
                    }else {
                        startBttn.setText("START");
                        Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.focusmodedialog);
                        dialog.show();
                        new Handler().postDelayed(() -> {
                            dialog.dismiss();
                        }, 5000);
                        isFocusing = true;
                        startBttn.setText("STOP");

                    }





                }else {
                    //get permission
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "enable this to lock screen!!");
                    startActivityForResult(intent, RESULT_ENABLE);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case RESULT_ENABLE:
                if (requestCode == Activity.RESULT_OK){
                    startBttn.setText("START");
                }else {
                    startBttn.setText("START");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
