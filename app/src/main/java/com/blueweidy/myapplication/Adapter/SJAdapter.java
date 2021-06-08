package com.blueweidy.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueweidy.myapplication.R;
import com.blueweidy.myapplication.subject;
import com.blueweidy.myapplication.updateData;

import java.util.List;

public class SJAdapter extends BaseAdapter{

    //region init var
    private Context context;
    private int layout;
    private List<subject> SJlist;

    private com.blueweidy.myapplication.updateData updateData;

    Button bttnPopup;
    //endregion

    public SJAdapter(Context context, int layout, List<subject> SJlist, updateData updateData) {
        this.context  = context;
        this.layout   = layout;
        this.SJlist   = SJlist;
        this.updateData = updateData;
    }

    private class ViewHolder{
        TextView txtname;
        ImageView ImgRename, ImgDelete;
    }

    @Override
    public int getCount() {
        return SJlist.size();
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
        //region init view
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtname   = convertView.findViewById(R.id.subjectname);
            holder.ImgDelete = convertView.findViewById(R.id.deleteBttn);
            holder.ImgRename = convertView.findViewById(R.id.renameBttn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final subject SJ = SJlist.get(position);
        holder.txtname.setText(SJ.getSubjectName());
        holder.txtname.setOnClickListener(v -> updateData.searchweb(true));
        //endregion

        //region Delete
        holder.ImgDelete.setOnClickListener(v -> updateData.updateValue(SJ.getSubjectName(), SJ.getSubjectID(), true));
        //endregion

        //region Rename
        holder.ImgRename.setOnClickListener(v -> updateData.updateSJData(SJ.getSubjectName(), SJ.getSubjectID(), true));
        //endregion

        //region Menu Popup
        bttnPopup = convertView.findViewById(R.id.bttnPopup);
        bttnPopup.setOnClickListener(v -> updateData.updatePopupMenuData(SJ.getSubjectName(), SJ.getSubjectID(), true));
        //endregion

        return convertView;
    }

}
