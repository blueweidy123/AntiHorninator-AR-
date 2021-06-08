package com.blueweidy.myapplication;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor mCursor;

    public ListProvider(Context applicationContext, Intent intent){
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        /*if (mCursor != null){
            mCursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        Uri uri = Contract.PATH_TODOS_URI;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                Contract._ID + " DESC");

        Binder.restoreCallingIdentity(identityToken);*/
    }

    @Override
    public void onDestroy() {
        if (mCursor != null){
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null ||
                !mCursor.moveToPosition(position)){
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_s_j);
        //rv.setTextViewText("R.id.widgetItemTaskNameLabel. mC");
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
