package com.tzf.junengtie.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.IBinder;

import com.tzf.junengtie.App;
import com.tzf.junengtie.util.SpManager;

import java.util.ArrayList;
import java.util.List;

public class FloatService extends Service {

    private InnerReceiver receiver;
    private App application;
    private ActivityManager activityManager;

    public static final String ISHOME = "com.tzf.action.IsHome";
    public static final String NOTHOME = "com.tzf.action.NotHome";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new InnerReceiver();
        IntentFilter filter = new IntentFilter();
        application = (App) getApplicationContext();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        filter.addAction(ISHOME);
        filter.addAction(NOTHOME);
        registerReceiver(receiver, filter);

        new AsyncTask<Void, Void, Void>() {

            private void send(boolean isHome) {
                if (isHome) {
                    FloatService.this.sendBroadcast(new Intent(ISHOME));
                } else {
                    FloatService.this.sendBroadcast(new Intent(NOTHOME));
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                boolean oldIsHome = false;

                while (true) {
                    boolean isHome = isHome();
                    if (oldIsHome != isHome) {
                        send(isHome);
                        oldIsHome = isHome;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    // 初始化浮动窗体
    private class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!SpManager.getInstance().getGlobalBoolean(SpManager.FLOAT_ICON, true)) {
                return;
            }

            String action = intent.getAction();
            if (ISHOME.equals(action)) {
                application.removedisplayFloatImageView();
            } else if (NOTHOME.equals(action) && !isSelfApplication()) {
                application.displayFloatImageView();
            }
        }

    }

    public boolean isSelfApplication() {
        ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
        String packageName = (cn == null) ? null : cn.getPackageName();
        if (packageName != null && packageName.equals(application.getPackageName())) {
            return true;
        }

        return false;
    }

    // 判断是否是桌面
    public boolean isHome() {
        List<String> homes = getHomes();
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(Integer.MAX_VALUE);
        return homes.contains(rti.get(0).topActivity.getPackageName());
    }

    // 获取所有桌面的Activity的包名
    private List<String> getHomes() {
        List<String> packages = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfo) {
            packages.add(info.activityInfo.packageName);
        }
        return packages;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}