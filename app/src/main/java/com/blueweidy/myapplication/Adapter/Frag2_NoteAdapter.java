package com.blueweidy.myapplication.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blueweidy.myapplication.AlarmReceiver;
import com.blueweidy.myapplication.Frag3_Alarm;
import com.blueweidy.myapplication.R;
import com.blueweidy.myapplication.note_update;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Calendar;

public class Frag2_NoteAdapter extends RecyclerView.Adapter<Frag2_NoteAdapter.NoteViewHolder>{

    private Context mContext;
    private ArrayList<Frag3_Alarm> listAlarm;
    private int layout;

    private Animation disappear, appear;

    private note_update noteUpdate;

    public Frag2_NoteAdapter(Context mContext,int layout, ArrayList<Frag3_Alarm> AlarmList, note_update note_update) {
        this.mContext   = mContext;
        this.listAlarm  = AlarmList;
        this.layout     = layout;
        this.noteUpdate = note_update;
    }


    @Override
    public NoteViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag2_single_row_timetable, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final NoteViewHolder holder, int position) {
        final Frag3_Alarm alarm = listAlarm.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.set(0,0,0,alarm.getTime_of_day(), alarm.getNote_minute());
        holder.time_tx.setText(DateFormat.format("hh:mm aa", calendar));
        if (alarm == null){
            return;
        }

        disappear = AnimationUtils.loadAnimation(mContext, R.anim.disappear_2);
        appear = AnimationUtils.loadAnimation(mContext, R.anim.appear_2);
        holder.checkBoxR.setOnClickListener(v -> {
            if (((CompoundButton) v).isChecked()){
                //region button animation
                holder.button_mon.startAnimation(disappear);
                holder.button_tue.startAnimation(disappear);
                holder.button_wed.startAnimation(disappear);
                holder.button_thu.startAnimation(disappear);
                holder.button_fri.startAnimation(disappear);
                holder.button_sat.startAnimation(disappear);
                holder.button_sun.startAnimation(disappear);
                holder.button_mon.setVisibility(View.GONE);
                holder.button_tue.setVisibility(View.GONE);
                holder.button_wed.setVisibility(View.GONE);
                holder.button_thu.setVisibility(View.GONE);
                holder.button_fri.setVisibility(View.GONE);
                holder.button_sat.setVisibility(View.GONE);
                holder.button_sun.setVisibility(View.GONE);
                //endregion
            }else {
                //region button animation
                holder.button_mon.setVisibility(View.VISIBLE);
                holder.button_sun.setVisibility(View.VISIBLE);
                holder.button_tue.setVisibility(View.VISIBLE);
                holder.button_wed.setVisibility(View.VISIBLE);
                holder.button_thu.setVisibility(View.VISIBLE);
                holder.button_fri.setVisibility(View.VISIBLE);
                holder.button_sat.setVisibility(View.VISIBLE);
                holder.button_mon.startAnimation(appear);
                holder.button_tue.startAnimation(appear);
                holder.button_wed.startAnimation(appear);
                holder.button_thu.startAnimation(appear);
                holder.button_fri.startAnimation(appear);
                holder.button_sat.startAnimation(appear);
                holder.button_sun.startAnimation(appear);
                //endregion
            }
            if (holder.checkBoxR.isChecked()){
                noteUpdate.repeat(alarm.getnID(), 1, true);
            }else{
                noteUpdate.repeat(alarm.getnID(), 0, true);
            }
        });

        //region set title
        if (alarm.getstate() == 0){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                holder.foldingCell.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm_disabled));
            }else {
                holder.foldingCell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm_disabled));
            }
        }else if (alarm.getstate() == 1){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                holder.foldingCell.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm));
            }else {
                holder.foldingCell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm));
            }
        }

        if (alarm.getMon() == 1){
            holder.button_mon.setBackgroundResource(R.drawable.filled_circled_m);
        }else if (alarm.getMon() == 0){
            holder.button_mon.setBackgroundResource(R.drawable.circled_m);
        }
        if (alarm.getTue() == 1){
            holder.button_tue.setBackgroundResource(R.drawable.filled_circled_t);
        }else if (alarm.getTue() == 0){
            holder.button_tue.setBackgroundResource(R.drawable.circled_t);
        }
        if (alarm.getWed() == 1){
            holder.button_wed.setBackgroundResource(R.drawable.filled_circled_w);
        }else if (alarm.getWed() == 0){
            holder.button_wed.setBackgroundResource(R.drawable.circled_w);
        }
        if (alarm.getThu() == 1){
            holder.button_thu.setBackgroundResource(R.drawable.filled_circled_t);
        }else if (alarm.getThu() == 0){
            holder.button_thu.setBackgroundResource(R.drawable.circled_t);
        }
        if (alarm.getFri() == 1){
            holder.button_fri.setBackgroundResource(R.drawable.filled_circled_f);
        }else if (alarm.getFri() == 0){
            holder.button_fri.setBackgroundResource(R.drawable.circled_f);
        }
        if (alarm.getSat() == 1){
            holder.button_sat.setBackgroundResource(R.drawable.filled_circled_s);
        }else if (alarm.getSat() == 0){
            holder.button_sat.setBackgroundResource(R.drawable.circled_s);
        }
        if (alarm.getSun() == 1){
            holder.button_sun.setBackgroundResource(R.drawable.filled_circled_s);
        }else if (alarm.getSun() == 0){
            holder.button_sun.setBackgroundResource(R.drawable.circled_s);
        }

        if (alarm.getRepeat()==1){
            holder.checkBoxR.setChecked(true);
            holder.button_sun.setVisibility(View.GONE);
            holder.button_mon.setVisibility(View.GONE);
            holder.button_tue.setVisibility(View.GONE);
            holder.button_wed.setVisibility(View.GONE);
            holder.button_thu.setVisibility(View.GONE);
            holder.button_fri.setVisibility(View.GONE);
            holder.button_sat.setVisibility(View.GONE);
        }else if (alarm.getRepeat()==0){
            holder.checkBoxR.setChecked(false);
            holder.button_sun.setVisibility(View.VISIBLE);
            holder.button_mon.setVisibility(View.VISIBLE);
            holder.button_tue.setVisibility(View.VISIBLE);
            holder.button_wed.setVisibility(View.VISIBLE);
            holder.button_thu.setVisibility(View.VISIBLE);
            holder.button_fri.setVisibility(View.VISIBLE);
            holder.button_sat.setVisibility(View.VISIBLE);
        }

        if (alarm.getstate() == 1){
            holder.switch_content.setChecked(true);
            holder.switch_title.setChecked(true);
        }else if (alarm.getstate() == 0){
            holder.switch_content.setChecked(false);
            holder.switch_title.setChecked(false);
        }
        //endregion

        //region button handle
        holder.button_sun.setOnClickListener(v -> {
            if (holder.button_sun.isChecked()){
                holder.button_sun.setBackgroundResource(R.drawable.filled_circled_s);
            }else {
                holder.button_sun.setBackgroundResource(R.drawable.circled_s);
            }
            if (holder.button_sun.isChecked()){
                noteUpdate.sundaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.sundaySet(alarm.getnID(), 0, true);
            }
        });
        holder.button_mon.setOnClickListener(v -> {
            if (holder.button_mon.isChecked()){
                holder.button_mon.setBackgroundResource(R.drawable.filled_circled_m);
            }else {
                holder.button_mon.setBackgroundResource(R.drawable.circled_m);
            }
            if (holder.button_mon.isChecked()){
                noteUpdate.mondaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.mondaySet(alarm.getnID(), 0, true);
            }
        });
        holder.button_tue.setOnClickListener(v -> {
            if (holder.button_tue.isChecked()){
                holder.button_tue.setBackgroundResource(R.drawable.filled_circled_t);
            }else {
                holder.button_tue.setBackgroundResource(R.drawable.circled_t);
            }
            if (holder.button_tue.isChecked()){
                noteUpdate.tuedaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.tuedaySet(alarm.getnID(), 0, true);
            }
        });
        holder.button_wed.setOnClickListener(v -> {
            if (holder.button_wed.isChecked()){
                holder.button_wed.setBackgroundResource(R.drawable.filled_circled_w);
            }else {
                holder.button_wed.setBackgroundResource(R.drawable.circled_w);
            }
            if (holder.button_wed.isChecked()){
                noteUpdate.wednesdaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.wednesdaySet(alarm.getnID(), 0, true);
            }
        });
        holder.button_thu.setOnClickListener(v -> {
            if (holder.button_thu.isChecked()){
                holder.button_thu.setBackgroundResource(R.drawable.filled_circled_t);
            }else {
                holder.button_thu.setBackgroundResource(R.drawable.circled_t);
            }
            if (holder.button_thu.isChecked()){
                noteUpdate.thursdaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.thursdaySet(alarm.getnID(), 0, true);
            }
        });
        holder.button_fri.setOnClickListener(v -> {
            if (holder.button_fri.isChecked()){
                holder.button_fri.setBackgroundResource(R.drawable.filled_circled_f);
            }else {
                holder.button_fri.setBackgroundResource(R.drawable.circled_f);
            }
            if (holder.button_fri.isChecked()){
                noteUpdate.fridaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.fridaySet(alarm.getnID(), 0, true);
            }
        });
        holder.button_sat.setOnClickListener(v -> {
            if (holder.button_sat.isChecked()){
                holder.button_sat.setBackgroundResource(R.drawable.filled_circled_s);
            }else {
                holder.button_sat.setBackgroundResource(R.drawable.circled_s);
            }
            if (holder.button_sat.isChecked()){
                noteUpdate.saturdaySet(alarm.getnID(), 1, true);
            }else {
                noteUpdate.saturdaySet(alarm.getnID(), 0, true);
            }
        });

        holder.time_tx.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    mContext,
                    (view, hourOfDay, minute) -> noteUpdate.updateTime(hourOfDay, minute, alarm.getnID(), true),12, 0, false
            );
            timePickerDialog.updateTime(alarm.getTime_of_day(),alarm.getNote_minute());
            timePickerDialog.show();
        });

        holder.note_tv.setText(alarm.getnName());
        holder.note_tv.setOnClickListener(v -> noteUpdate.updateNoteName(alarm.getnName(), alarm.getnID(), true));

        holder.button.setOnClickListener(v -> holder.foldingCell.toggle(false));

        holder.button2.setOnClickListener(v -> holder.foldingCell.toggle(false));

        holder.switch_content.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()){
                holder.switch_title.setChecked(true);
                noteUpdate.turn(alarm.getnID(), 1, true);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    holder.foldingCell.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm));
                }else {
                    holder.foldingCell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm));
                }
            }else {
                holder.switch_title.setChecked(false);
                noteUpdate.turn(alarm.getnID(), 0, true);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    holder.foldingCell.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm_disabled));
                }else {
                    holder.foldingCell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm_disabled));
                }
            }
        });

        holder.delete.setOnClickListener(v -> noteUpdate.delete(alarm.getnName(), alarm.getnID(), true));

        holder.switch_title.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()){
                holder.switch_content.setChecked(true);
                noteUpdate.turn(alarm.getnID(), 1, true);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    holder.foldingCell.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm));
                }else {
                    holder.foldingCell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm));
                }
            }else {
                holder.switch_content.setChecked(false);
                noteUpdate.turn(alarm.getnID(), 0, true);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    holder.foldingCell.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm_disabled));
                }else {
                    holder.foldingCell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_round_alarm_disabled));
                }
                cancelAlarm(alarm.getnID());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listAlarm == null){
            return 0;
        }else {
            return listAlarm.size();
        }

    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private FoldingCell foldingCell;
        //tittext time
        private TextView time_tx;
        //
        private TextView note_tv;
        //
        private TextView tit_cal;
        //
        private Button button;
        //
        private Button button2;
        //
        private Switch switch_title;
        //
        private Switch switch_content;
        //
        private CheckBox checkBoxR;
        //
        private ToggleButton button_sun;
        //
        private ToggleButton button_mon;
        //
        private ToggleButton button_tue;
        //
        private ToggleButton button_wed;
        //
        private ToggleButton button_thu;
        //
        private ToggleButton button_fri;
        //
        private ToggleButton button_sat;
        //
        private TextView delete;
        public NoteViewHolder(View itemView) {
            super(itemView);

            foldingCell    = itemView.findViewById(R.id.view_folding);
            time_tx        = itemView.findViewById(R.id.time_text);
            note_tv        = itemView.findViewById(R.id.note_text);
            tit_cal        = itemView.findViewById(R.id.text_calendal2);
            button         = itemView.findViewById(R.id.button_expand);
            button2        = itemView.findViewById(R.id.button_expand2);
            delete         = itemView.findViewById(R.id.deleteBttn_alarm);
            button_sun     = itemView.findViewById(R.id.button_sun);
            button_mon     = itemView.findViewById(R.id.button_mon);
            button_tue     = itemView.findViewById(R.id.button_tue);
            button_wed     = itemView.findViewById(R.id.button_wed);
            button_thu     = itemView.findViewById(R.id.button_thu);
            button_fri     = itemView.findViewById(R.id.button_fri);
            button_sat     = itemView.findViewById(R.id.button_sat);
            checkBoxR      = itemView.findViewById(R.id.checkbox_S);
            switch_content = itemView.findViewById(R.id.note_switch_content);
            switch_title   = itemView.findViewById(R.id.note_switch_title);
        }
    }

    private void cancelAlarm(int id){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
