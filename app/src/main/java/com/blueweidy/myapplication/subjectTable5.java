package com.blueweidy.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blueweidy.myapplication.Adapter.SJAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//=====MONDAY FRAGMENT=====

public class subjectTable5 extends Fragment implements View.OnClickListener, updateData{

    //region init var
    private ListView SJlistview;
    ArrayList<subject> SJArray;
    private SJAdapter adapter;

    //private Database database;
    Database_sj database;

    private FloatingActionButton editBttn;
    private FloatingActionButton addBttn;
    private FloatingActionButton voiceBttn;
    private Animation move_up, move_down, move_left, move_right;

    private EditText et_voice;

    private static final int RECOGNIZER_RESULT = 1;

    private static boolean isEditing = false;
    //endregion



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_subject_table, container, false);

        database = new Database_sj(getActivity());

        SJlistview = view.findViewById((R.id.SJlistview));
        SJArray = new ArrayList<>();
        adapter = new SJAdapter(getActivity(), R.layout.single_row_subject, SJArray, this);
        SJlistview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        GetData();

        //region animation
        move_up = AnimationUtils.loadAnimation(getActivity(), R.anim.move_up);
        move_down = AnimationUtils.loadAnimation(getActivity(), R.anim.move_down);
        move_left = AnimationUtils.loadAnimation(getActivity(), R.anim.move_left);
        move_right = AnimationUtils.loadAnimation(getActivity(), R.anim.move_right);


        editBttn = view.findViewById(R.id.editBttn);
        editBttn.setOnClickListener(this);

        voiceBttn = view.findViewById(R.id.voiceBttn);
        voiceBttn.setOnClickListener(this);
        et_voice = view.findViewById(R.id.ed_speechToText);

        addBttn = view.findViewById(R.id.addBttn);
        addBttn.setOnClickListener(this);
        //endregion
        return view;
    }

    private void GetData(){
        SJArray.clear();
        SJArray.addAll(database.getAll_fri_Subjects());
        adapter.notifyDataSetChanged();
    }

    private void voiceRegDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_voice_recog);
        Button hearing = dialog.findViewById(R.id.voiceStartHearing);
        hearing.setOnClickListener(v -> {
            Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speach to text");
            startActivityForResult(speechIntent, RECOGNIZER_RESULT);

        });
        dialog.show();
    }
    private void voiceReg(){
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speach to text");
        startActivityForResult(speechIntent, RECOGNIZER_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == getActivity().RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            database.insert(5, matches.get(0).toString());
            //Toast.makeText(getActivity(), matches.get(0).toString(), Toast.LENGTH_SHORT).show();
            GetData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AddDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);
        final EditText editN = dialog.findViewById(R.id.edittextADD);
        Button addBttn = dialog.findViewById(R.id.addBttnDialog);
        addBttn.setOnClickListener(v -> {
            String name = editN.getText().toString();
            if (name.equals("")){
                database.insert(5, "null");
                dialog.dismiss();
                GetData();
            }else {
                database.insert(5, name);
                dialog.dismiss();
                GetData();
            }
        });
        Button cancelBttn = dialog.findViewById(R.id.cancelBttn);
        cancelBttn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void RenameDialog(String name, final int id){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rename);
        final EditText editRN = dialog.findViewById(R.id.edittextRENAME);
        Button confirmBttn = dialog.findViewById(R.id.Confirmrename);
        editRN.setText(name);
        confirmBttn.setOnClickListener(v -> {
            String newName = editRN.getText().toString().trim();
            database.updateName(5, newName, id);
            dialog.dismiss();
            GetData();
        });
        Button cancelRnBttn = dialog.findViewById(R.id.cancelRENAME);
        cancelRnBttn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void DeleteDialog(final String name, final int Id){
        AlertDialog.Builder deleteDia = new AlertDialog.Builder(getActivity());
        deleteDia.setMessage("Xóa: "+name+ "?");
        deleteDia.setPositiveButton("Yes", (dialog, which) -> {
            database.delete(5, Id);
            Toast.makeText(getActivity(), "Deleted " + name, Toast.LENGTH_SHORT).show();
            GetData();
        });
        deleteDia.setNegativeButton("No", (dialog, which) -> {

        });
        deleteDia.show();
    }

    private void PickDialod(final int Id){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_picksubject);
        Button button1 = dialog.findViewById(R.id.sjButton_toan);
        Button button2 = dialog.findViewById(R.id.sjButton_dai);
        Button button3 = dialog.findViewById(R.id.sjButton_hinh);
        Button button4 = dialog.findViewById(R.id.sjButton_van);
        Button button5 = dialog.findViewById(R.id.sjButton_anh);
        Button button6 = dialog.findViewById(R.id.sjButton_sinh);
        Button button7 = dialog.findViewById(R.id.sjButton_su);
        Button button8 = dialog.findViewById(R.id.sjButton_dia);
        Button button9 = dialog.findViewById(R.id.sjButton_hoa);
        Button button10 = dialog.findViewById(R.id.sjButton_li);
        Button button11 = dialog.findViewById(R.id.sjButton_tin);
        Button button12 = dialog.findViewById(R.id.sjButton_gdcd);
        Button button13 = dialog.findViewById(R.id.sjButton_cn);
        Button button14 = dialog.findViewById(R.id.sjButton_triet);
        Button button15 = dialog.findViewById(R.id.sjButton_gdqp_an);
        Button button16 = dialog.findViewById(R.id.sjButton_TD);
        Button button17 = dialog.findViewById(R.id.sjButton_CC);
        Button button18 = dialog.findViewById(R.id.sjButton_SHL);
        button1.setOnClickListener(v -> {
            String newName = "Toán";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button2.setOnClickListener(v -> {
            String newName = "Đại số";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button3.setOnClickListener(v -> {
            String newName = "Hình học";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button4.setOnClickListener(v -> {
            String newName = "Ngữ văn";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button5.setOnClickListener(v -> {
            String newName = "Ngoại ngữ";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button6.setOnClickListener(v -> {
            String newName = "Sinh học";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button7.setOnClickListener(v -> {
            String newName = "Lịch sử";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button8.setOnClickListener(v -> {
            String newName = "Địa lí";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button9.setOnClickListener(v -> {
            String newName = "Hóa học";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button10.setOnClickListener(v -> {
            String newName = "Vật lí";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button11.setOnClickListener(v -> {
            String newName = "Tin học";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button12.setOnClickListener(v -> {
            String newName = "GDCD";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button13.setOnClickListener(v -> {
            String newName = "Công nghệ";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button14.setOnClickListener(v -> {
            String newName = "Triết học";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button15.setOnClickListener(v -> {
            String newName = "GDQP-AN";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button16.setOnClickListener(v -> {
            String newName = "Thể dục";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button17.setOnClickListener(v -> {
            String newName = "Chào cờ";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        button18.setOnClickListener(v -> {
            String newName = "Sinh hoạt lớp";
            database.updateName(5, newName, Id);
            dialog.dismiss();
            GetData();
        });
        dialog.show();
    }

    private void editBttnHandler(){
        if (!isEditing){
            isEditing = true;
            editBttn.setImageResource(R.drawable.ic_done);
            addBttn.startAnimation(move_up);
            voiceBttn.startAnimation(move_left);
        }else if (isEditing){
            isEditing = false;
            editBttn.setImageResource(R.drawable.ic_change);
            addBttn.startAnimation(move_down);
            addBttn.setVisibility(View.INVISIBLE);
            voiceBttn.startAnimation(move_right);
            voiceBttn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addBttn: {
                if (isEditing){
                    AddDialog();
                }
                break;
            }
            case R.id.editBttn: {
                editBttnHandler();
                break;
            }
            case R.id.voiceBttn:{
                voiceReg();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        GetData();
        super.onStart();
    }

    //region Interface implementation
    //Delete
    @Override
    public void updateValue(String SJName,int Id, boolean updatevalue) {
    if (updatevalue){
            DeleteDialog(SJName,Id);
        }
    }
    //Rename
    @Override
    public void updateSJData(String Name, int id, boolean updateRN) {
        if (updateRN){
            RenameDialog(Name, id);
        }
    }
    //menuSJ
    @Override
    public void updatePopupMenuData(String name, int Id, boolean updatePUMD) {
        if (updatePUMD){
            PickDialod(Id);
        }
    }

    ////endregion

    private void voiceRegLesson(){
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speach to text");
        startActivityForResult(speechIntent, RECOGNIZER_RESULT);
    }

    @Override
    public void searchweb(boolean SW) {
        if (SW){
            voiceRegLesson();
        }
    }
}
