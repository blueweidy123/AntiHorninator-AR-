package com.blueweidy.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RCVer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (fullScreenActvity.FULL_SCREEN_ACTION.equals(intent.getAction()));

    }
}
