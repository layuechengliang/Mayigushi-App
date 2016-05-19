package com.tzf.libo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.apkfuns.logutils.LogUtils;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.InputItem;
import com.tzf.libo.util.DBConstants;
import java.util.List;
import cn.jpush.android.api.JPushInterface;

/**
 * @author tangzhifei on 15/11/15.
 */
public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);

        // LogUtils 配置
        LogUtils.configAllowLog = BuildConfig.DEBUG;
        LogUtils.configTagPrefix = "Mayi-";

        MQManager.init(context, "d25f5ee0c0a9b0e6ee2cf9cfa9f46f58", new OnInitCallback() {

            @Override
            public void onSuccess(String clientId) {
                LogUtils.e("meiqia onSuccess.");
            }

            @Override
            public void onFailure(int code, String message) {
                LogUtils.e("meiqia onFailure.");
            }

        });


        initDBData(this);
    }

    public static void initDBData(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            DBTableManager dbTableManager = DBTableManager.getInstance(context);
            int type = DBConstants.COMMON_ITEM_MONEYTYPE;
            List<InputItem> list = DBTableManager.getInstance(context).selectCommonItem(type);
            DataManager.setBaseMap(list, type);
            type = DBConstants.COMMON_ITEM_REASON;
            list = dbTableManager.selectCommonItem(type);
            DataManager.setBaseMap(list, type);
            type = DBConstants.COMMON_ITEM_SENDTYPE;
            list = dbTableManager.selectCommonItem(type);
            DataManager.setBaseMap(list, type);
            type = DBConstants.COMMON_ITEM_SUBJECT;
            list = dbTableManager.selectCommonItem(type);
            DataManager.setBaseMap(list, type);
        }
    }

}
