package com.tzf.junengtie.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.tzf.junengtie.db.Message;
import com.tzf.junengtie.util.SpManager;
import com.tzf.junengtie.util.StringUtil;
import com.tzf.junengtie.util.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

/**
 * Created by tangzhifei on 15/11/3.
 */
public class ClipboardService extends Service {

    private Timer timer;
    private ClipboardManager cm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        timer = new Timer();
        timer.schedule(new MyTimer(), 0, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    class MyTimer extends TimerTask {

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void run() {
            if (cm.hasPrimaryClip()) {
                String content = cm.getPrimaryClip().getItemAt(0).getText().toString();
                String spContent = SpManager.getInstance().getGlobalString(SpManager.LAST_CLIPBOARD_CONTENT, StringUtil.EMPTY_STRING);
                if (!StringUtil.isEmpty(content) && !StringUtil.isEquals(content, spContent)) {
                    SpManager.getInstance().setGlobalString(SpManager.LAST_CLIPBOARD_CONTENT, content);

                    Message message = new Message();
                    message.setContent(content);
                    message.setStatus(0);
                    message.setUpdateTime(TimeUtil.getCurrentTimeInLong());
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealm(message);
                    realm.commitTransaction();
                    realm.close();
                }
            }
        }

    }

}