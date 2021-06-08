package com.blueweidy.myapplication;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class Frag2_timeTest extends Fragment {

    private View view;
    TextView textViewTimer1, textViewTimer2;
    int t1H, t1M, t2H, t2M;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag2_time_test, container, false);

        textViewTimer1 = view.findViewById(R.id.tv_timer1);
        textViewTimer2 = view.findViewById(R.id.tv_timer2);

        textViewTimer1.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(),
                    (view, hourOfDay, minute) -> {
                        t1H = hourOfDay;
                        t1M = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, t1H, t1M);
                        textViewTimer1.setText(DateFormat.format("hh:mm aa", calendar));
                    },12, 0, false
            );
            timePickerDialog.updateTime(t1H, t1M);
            timePickerDialog.show();

        });

        textViewTimer2.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    t2H = hourOfDay;
                    t2M = minute;
                    String time = t2H + ":" +t2M;
                    SimpleDateFormat f24h = new SimpleDateFormat("HH:mm");
                    try {
                        Date date = f24h.parse(time);
                        SimpleDateFormat f12h = new SimpleDateFormat("hh:mm aa");
                        textViewTimer2.setText(f12h.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, 12,0,false
            );
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.show();
        });


        return view;
    }
}
