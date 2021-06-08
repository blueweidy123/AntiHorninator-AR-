package com.blueweidy.myapplication.Adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.blueweidy.myapplication.R;
import com.blueweidy.myapplication.Frag2_Notes;
import com.blueweidy.myapplication.note_update;
import com.ramotion.foldingcell.FoldingCell;

import java.util.Calendar;
import java.util.List;

public class Frag3_noteAdapter extends BaseAdapter {

    //region var init
    private Context context;
    private int layout;
    private List<Frag2_Notes> notes_list;
    //interface
    private note_update noteUpdate_I;
    //
    private static boolean isEditing = false;
    //
    private Animation disappear, appear;
    //endregion

    public Frag3_noteAdapter(Context context, int layout, List<Frag2_Notes> notes_list, note_update noteUpdate_I) {
        this.context      = context;
        this.layout       = layout;
        this.notes_list   = notes_list;
        this.noteUpdate_I = noteUpdate_I;
    }

    private class ViewHolder{
        //region view holder init
        //delete note
        ImageView delete;
        //check repeat?
        CheckBox date_note_cb;
        //button set repeat day
        ToggleButton d2, d3, d4, d5, d6, d7, d8;
        //title time text
        TextView title_time;
        //time pick
        TextView content_time;
        //title note
        TextView title_note;
        //content note
        TextView content_note;
        //folding view
        FoldingCell foldingCell;
        //expand button
        Button expB;
        //colapse button
        Button colB;
        //edit button
        ImageView edbN;
        //
        ConstraintLayout tit_lo;
        //
        ConstraintLayout content_lo;
        //endregion
    }

    @Override
    public int getCount() {
        return notes_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        //region init view
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            try {
                convertView = inflater.inflate(layout, null);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            //region holder init
            holder.tit_lo       = convertView.findViewById(R.id.note_title);
            holder.content_lo   = convertView.findViewById(R.id.menubar_notes);
            holder.title_time   = convertView.findViewById(R.id.note_timeshow_tv);
            holder.title_note   = convertView.findViewById(R.id.text_title_note);
            holder.content_note = convertView.findViewById(R.id.note_textview);
            holder.content_time = convertView.findViewById(R.id.content_time_pick);
            holder.foldingCell  = convertView.findViewById(R.id.note_folding);
            holder.expB         = convertView.findViewById(R.id.expand_note_title);
            holder.colB         = convertView.findViewById(R.id.colapse_note_fold);
            holder.edbN         = convertView.findViewById(R.id.edit_note_button);
            holder.delete       = convertView.findViewById(R.id.delete_notes_bttn);
            holder.date_note_cb = convertView.findViewById(R.id.date_note_checkbox);
            holder.d2           = convertView.findViewById(R.id.button_d2);
            holder.d3           = convertView.findViewById(R.id.button_d3);
            holder.d4           = convertView.findViewById(R.id.button_d4);
            holder.d5           = convertView.findViewById(R.id.button_d5);
            holder.d6           = convertView.findViewById(R.id.button_d6);
            holder.d7           = convertView.findViewById(R.id.button_d7);
            holder.d8           = convertView.findViewById(R.id.button_d8);
            //endregion

            //region animation init
            disappear = AnimationUtils.loadAnimation(context, R.anim.disappear_2);
            appear = AnimationUtils.loadAnimation(context, R.anim.appear_2);
            convertView.setTag(holder);
            //endregion

        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //endregion

        final Frag2_Notes notes = notes_list.get(position);

        //region set time
        holder.content_note.setText(notes.getnName());
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, notes.getTime_of_day(), notes.getNote_minute());
        holder.content_time.setText(DateFormat.format("hh:mm aa", calendar));
        holder.title_time.setText(DateFormat.format("hh:mm aa", calendar));
        //endregion

        //region day-pick button handle
        holder.d8.setOnClickListener(v -> {
            if (holder.d8.isChecked()){
                holder.d8.setBackgroundResource(R.drawable.filled_circled_s);
            }else {
                holder.d8.setBackgroundResource(R.drawable.circled_s);
            }
            if (holder.d8.isChecked()){
                noteUpdate_I.sundaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.sundaySet(notes.getnID(), 0, true);
            }
        });
        holder.d2.setOnClickListener(v -> {
            if (holder.d2.isChecked()){
                holder.d2.setBackgroundResource(R.drawable.filled_circled_m);
            }else {
                holder.d2.setBackgroundResource(R.drawable.circled_m);
            }
            if (holder.d2.isChecked()){
                noteUpdate_I.mondaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.mondaySet(notes.getnID(), 0, true);
            }
        });
        holder.d3.setOnClickListener(v -> {
            if (holder.d3.isChecked()){
                holder.d3.setBackgroundResource(R.drawable.filled_circled_t);
            }else {
                holder.d3.setBackgroundResource(R.drawable.circled_t);
            }
            if (holder.d3.isChecked()){
                noteUpdate_I.tuedaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.tuedaySet(notes.getnID(), 0, true);
            }
        });
        holder.d4.setOnClickListener(v -> {
            if (holder.d4.isChecked()){
                holder.d4.setBackgroundResource(R.drawable.filled_circled_w);
            }else {
                holder.d4.setBackgroundResource(R.drawable.circled_w);
            }
            if (holder.d4.isChecked()){
                noteUpdate_I.wednesdaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.wednesdaySet(notes.getnID(), 0, true);
            }
        });
        holder.d5.setOnClickListener(v -> {
            if (holder.d5.isChecked()){
                holder.d5.setBackgroundResource(R.drawable.filled_circled_t);
            }else {
                holder.d5.setBackgroundResource(R.drawable.circled_t);
            }
            if (holder.d5.isChecked()){
                noteUpdate_I.thursdaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.thursdaySet(notes.getnID(), 0, true);
            }
        });
        holder.d6.setOnClickListener(v -> {
            if (holder.d6.isChecked()){
                holder.d6.setBackgroundResource(R.drawable.filled_circled_f);
            }else {
                holder.d6.setBackgroundResource(R.drawable.circled_f);
            }
            if (holder.d6.isChecked()){
                noteUpdate_I.fridaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.fridaySet(notes.getnID(), 0, true);
            }
        });
        holder.d7.setOnClickListener(v -> {
            if (holder.d7.isChecked()){
                holder.d7.setBackgroundResource(R.drawable.filled_circled_s);
            }else {
                holder.d7.setBackgroundResource(R.drawable.circled_s);
            }
            if (holder.d7.isChecked()){
                noteUpdate_I.saturdaySet(notes.getnID(), 1, true);
            }else {
                noteUpdate_I.saturdaySet(notes.getnID(), 0, true);
            }
        });
        //endregion

        //region check box set button animation
        holder.date_note_cb.setOnClickListener(v -> {
            if (((CompoundButton) v).isChecked()){
                //region button animation
                holder.d2.startAnimation(disappear);
                holder.d3.startAnimation(disappear);
                holder.d4.startAnimation(disappear);
                holder.d5.startAnimation(disappear);
                holder.d6.startAnimation(disappear);
                holder.d7.startAnimation(disappear);
                holder.d8.startAnimation(disappear);
                holder.d2.setVisibility(View.GONE);
                holder.d3.setVisibility(View.GONE);
                holder.d4.setVisibility(View.GONE);
                holder.d5.setVisibility(View.GONE);
                holder.d6.setVisibility(View.GONE);
                holder.d7.setVisibility(View.GONE);
                holder.d8.setVisibility(View.GONE);
                //endregion
            }else {
                //region button animation
                holder.d2.setVisibility(View.VISIBLE);
                holder.d8.setVisibility(View.VISIBLE);
                holder.d3.setVisibility(View.VISIBLE);
                holder.d4.setVisibility(View.VISIBLE);
                holder.d5.setVisibility(View.VISIBLE);
                holder.d6.setVisibility(View.VISIBLE);
                holder.d7.setVisibility(View.VISIBLE);
                holder.d2.startAnimation(appear);
                holder.d3.startAnimation(appear);
                holder.d4.startAnimation(appear);
                holder.d5.startAnimation(appear);
                holder.d6.startAnimation(appear);
                holder.d7.startAnimation(appear);
                holder.d8.startAnimation(appear);
                //endregion
            }
            if (holder.date_note_cb.isChecked()){
                noteUpdate_I.repeat(notes.getnID(), 1, true);
                noteUpdate_I.mondaySet(notes.getnID(), 1, true);
                noteUpdate_I.tuedaySet(notes.getnID(), 1, true);
                noteUpdate_I.wednesdaySet(notes.getnID(), 1, true);
                noteUpdate_I.thursdaySet(notes.getnID(), 1, true);
                noteUpdate_I.fridaySet(notes.getnID(), 1, true);
                noteUpdate_I.saturdaySet(notes.getnID(), 1, true);
                noteUpdate_I.sundaySet(notes.getnID(), 1, true);
            }else{
                noteUpdate_I.repeat(notes.getnID(), 0, true);
            }
        });
        //endregion

        //region delete
        holder.delete.setOnClickListener(v -> noteUpdate_I.delete(notes.getnName(), notes.getnID(), true));
        //endregion

        //region expand view
        holder.expB.setOnClickListener(v -> holder.foldingCell.toggle(false));
        //endregion

        //region colapse view
        holder.colB.setOnClickListener(v -> holder.foldingCell.toggle(false));
        //endregion

        //region edit mode
        holder.edbN.setOnClickListener(v -> {
            if (!isEditing){
                isEditing = true;
                holder.edbN.setImageResource(R.drawable.ic_mode_edit_holo_blue_light_24dp);
            }else if (isEditing){
                isEditing = false;
                holder.edbN.setImageResource(R.drawable.ic_done_all_hbl_24dp);
            }
        });
        //endregion

        //region time pick
        holder.content_time.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    context,
                    (view, hourOfDay, minute) -> noteUpdate_I.updateTime(hourOfDay, minute, notes.getnID(), true),12, 0, false
            );
            timePickerDialog.updateTime(notes.getTime_of_day(), notes.getNote_minute());
            timePickerDialog.show();
        });
        //endregion

        //region rename
        holder.content_note.setOnClickListener(v -> noteUpdate_I.updateNoteName(notes.getnName(), notes.getnID(), true));
        //endregion

        //region set title
        holder.title_note.setText(holder.content_note.getText().toString());

        if (notes.getMon() == 1){
            holder.d2.setBackgroundResource(R.drawable.filled_circled_m);
        }else if (notes.getMon() == 0){
            holder.d2.setBackgroundResource(R.drawable.circled_m);
        }
        if (notes.getTue() == 1){
            holder.d3.setBackgroundResource(R.drawable.filled_circled_t);
        }else if (notes.getTue() == 0){
            holder.d3.setBackgroundResource(R.drawable.circled_t);
        }
        if (notes.getWed() == 1){
            holder.d4.setBackgroundResource(R.drawable.filled_circled_w);
        }else if (notes.getWed() == 0){
            holder.d4.setBackgroundResource(R.drawable.circled_w);
        }
        if (notes.getThu() == 1){
            holder.d5.setBackgroundResource(R.drawable.filled_circled_t);
        }else if (notes.getThu() == 0){
            holder.d5.setBackgroundResource(R.drawable.circled_t);
        }
        if (notes.getFri() == 1){
            holder.d6.setBackgroundResource(R.drawable.filled_circled_f);
        }else if (notes.getFri() == 0){
            holder.d6.setBackgroundResource(R.drawable.circled_f);
        }
        if (notes.getSat() == 1){
            holder.d7.setBackgroundResource(R.drawable.filled_circled_s);
        }else if (notes.getSat() == 0){
            holder.d7.setBackgroundResource(R.drawable.circled_s);
        }
        if (notes.getSun() == 1){
            holder.d8.setBackgroundResource(R.drawable.filled_circled_s);
        }else if (notes.getSun() == 0){
            holder.d8.setBackgroundResource(R.drawable.circled_s);
        }

        if (notes.getRepeat()==1){
            holder.date_note_cb.setChecked(true);
            holder.d8.setVisibility(View.GONE);
            holder.d2.setVisibility(View.GONE);
            holder.d3.setVisibility(View.GONE);
            holder.d4.setVisibility(View.GONE);
            holder.d5.setVisibility(View.GONE);
            holder.d6.setVisibility(View.GONE);
            holder.d7.setVisibility(View.GONE);
        }else if (notes.getRepeat()==0){
            holder.date_note_cb.setChecked(false);
            holder.d8.setVisibility(View.VISIBLE);
            holder.d2.setVisibility(View.VISIBLE);
            holder.d3.setVisibility(View.VISIBLE);
            holder.d4.setVisibility(View.VISIBLE);
            holder.d5.setVisibility(View.VISIBLE);
            holder.d6.setVisibility(View.VISIBLE);
            holder.d7.setVisibility(View.VISIBLE);
        }
        //endregion

        return convertView;
    }
}
