package com.tzf.junengtie.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tangzhifei on 15/11/3.
 */
public class ServiceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent();
        myIntent.setAction("com.tzf.junengtie.ClipboardService");
        context.startService(myIntent);
    }

}