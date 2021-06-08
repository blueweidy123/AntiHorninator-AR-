package com.blueweidy.myapplication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueweidy.myapplication.Adapter.Frag3_noteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class Frag3_notes extends Fragment implements note_update{

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    //region init var
    private ListView noteLV;
    private ArrayList<Frag2_Notes> notesList;
    private Frag3_noteAdapter adapter;

    private FloatingActionButton editBttn;
    private FloatingActionButton addBttn;
    private static boolean isEditing = false;
    private Animation move_up, move_down;

    private int h, m;

    private Database_sqlite database;

    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database_sqlite(getActivity());
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_frag3_notes, container, false);
        notesList = new ArrayList<>();
        noteLV = view.findViewById(R.id.noteListView);
        adapter = new Frag3_noteAdapter(getActivity(), R.layout.single_row_notes, notesList, this);
        noteLV.setAdapter(adapter);
        GetData();
        adapter.notifyDataSetChanged();

        //region on view button func
        editBttn = view.findViewById(R.id.editBttn_note);
        editBttn.setOnClickListener(v -> editBttnHandler());

        addBttn = view.findViewById(R.id.addBttn_note);
        addBttn.setOnClickListener(v -> AddDialog());
        //endregion
        move_up = AnimationUtils.loadAnimation(getActivity(), R.anim.move_up);
        move_down = AnimationUtils.loadAnimation(getActivity(), R.anim.move_down);
        //callIntent();
        return view;
    }

    public void SetAlarm(Frag2_Notes note){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, note.getTime_of_day());
        calendar.set(Calendar.MINUTE, note.getNote_minute());

        startAlarm(calendar, note);
    }

    private void startAlarm(Calendar c, Frag2_Notes note){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        //0: notification
        //1: alarm
        intent.putExtra("command", 0);
        intent.putExtra("id", note.getnID());
        intent.putExtra("title", note.getnName());
        intent.putExtra("repeat", note.getRepeat());
        intent.putExtra("hour", note.getTime_of_day());
        intent.putExtra("min", note.getNote_minute());
        intent.putExtra("mon", note.getMon());
        intent.putExtra("tue", note.getTue());
        intent.putExtra("wed", note.getWed());
        intent.putExtra("thu", note.getThu());
        intent.putExtra("fri", note.getFri());
        intent.putExtra("sat", note.getSat());
        intent.putExtra("sun", note.getSun());
        pendingIntent = PendingIntent.getBroadcast(getActivity(), note.getnID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        if (c.after(now)) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void cancelAlarm(int id){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public void getActiveNote(){
        for (int i = 0; i < notesList.size(); i++){
            Frag2_Notes note = notesList.get(i);
            SetAlarm(note);
        }
    }

    private void editBttnHandler(){
        if (!isEditing){
            isEditing = true;
            editBttn.setImageResource(R.drawable.ic_done);
            addBttn.startAnimation(move_up);
        }else if (isEditing){
            isEditing = false;
            editBttn.setImageResource(R.drawable.ic_change);
            addBttn.startAnimation(move_down);
            addBttn.setVisibility(View.INVISIBLE);
        }
    }

    private void AddDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_note);
        final EditText editN = dialog.findViewById(R.id.edittextNOTE);
        final TextView picktime = dialog.findViewById(R.id.addNote_timepick);
        picktime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(),
                    (view, hourOfDay, minute) -> {
                        h = hourOfDay;
                        m = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0, h, m);
                        picktime.setText(DateFormat.format("hh:mm aa", calendar));
                    },12,0,false
            );
            timePickerDialog.updateTime(h, m);
            timePickerDialog.show();
        });

        Button addBttn = dialog.findViewById(R.id.noteaddBttnDialog);
        addBttn.setOnClickListener(v -> {
            String name = editN.getText().toString();
            if (name.equals("")){
                database.insert("null", 00, 00, 0, 1,1,1,1,1,1,1);
                dialog.dismiss();
                GetData();
                getActiveNote();
            }else {
                database.insert(name, h, m, 0,1,1,1,1,1,1,1);
                dialog.dismiss();
                GetData();
                getActiveNote();
            }
        });
        Button cancelBttn = dialog.findViewById(R.id.notecancelBttn);
        cancelBttn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void GetData(){
        notesList.clear();
        notesList.addAll(database.getAllNote());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateNoteName(final String name, final int id, boolean update) {
        if (update){
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_change_note);
            final EditText editText = dialog.findViewById(R.id.edittext_note_chage);
            Button cfb = dialog.findViewById(R.id.Confirm_note_c);
            editText.setText(name);
            cfb.setOnClickListener(v -> {
                String nName = editText.getText().toString();
                database.updateName(nName, id);
                dialog.dismiss();
                GetData();
                getActiveNote();
            });
            Button dismiss = dialog.findViewById(R.id.cancel_note_change);
            dismiss.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        }
    }

    @Override
    public void updateTime(int hh, int mm, int id, boolean update) {
        if (update){
            database.updateTime(id, hh, mm);
            GetData();
            getActiveNote();
        }
    }

    @Override
    public void delete(final String name, final int id, boolean update) {
        if (update){
            AlertDialog.Builder deleteD = new AlertDialog.Builder(getActivity());
            deleteD.setMessage("DELETE " + name +".?");
            deleteD.setPositiveButton("Yes", (dialog, which) -> {
                cancelAlarm(id);
                database.delete(id);
                Toast.makeText(getActivity(), "Deleted " + name, Toast.LENGTH_SHORT).show();
                GetData();
                getActiveNote();
            });
            deleteD.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            deleteD.show();
        }
    }

    @Override
    public void repeat(int id, int Repeatboolean, boolean update) {
        if (update){
            database.updateRepeat(id, Repeatboolean);
            getActiveNote();
        }
    }

    @Override
    public void sundaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateSun(id, daySet);
        }
    }

    @Override
    public void mondaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateMon(id, daySet);
        }
    }

    @Override
    public void tuedaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateTue(id, daySet);
        }
    }

    @Override
    public void wednesdaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateWed(id, daySet);
        }
    }

    @Override
    public void thursdaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateThu(id, daySet);
        }
    }

    @Override
    public void fridaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateFri(id, daySet);
        }
    }

    @Override
    public void saturdaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateSat(id, daySet);
        }
    }

    @Override
    public void turn(int id, int switchboolean, boolean update) {
        //notes obj class dont have this atribute
    }

    @Override
    public void onStop() {
        super.onStop();
        getActiveNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActiveNote();
    }
}
