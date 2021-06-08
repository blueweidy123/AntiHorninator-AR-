package com.blueweidy.myapplication;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidgetSJ extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int [] appWidgetId) {
        for (int i = 0; i < appWidgetId.length; i++){
            //RemoteViews remoteViews = upda
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_s_j);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_s_j);
        Intent svcintent = new Intent(context, AppWidgetService.class);
        svcintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcintent.setData(Uri.parse(svcintent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(appWidgetId, R.id.app_wg_sj, svcintent);
        remoteViews.setEmptyView(R.id.app_wg_sj, R.id.emptyView);

        return remoteViews;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager, appWidgetId[]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

