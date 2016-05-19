package com.tzf.junengtie.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.tzf.junengtie.App;

/**
 * Created by tangzhifei on 15/9/19.
 */
public class SpManager {

    private static final String SP_FILE_NAME = "junengtie";

    public static final String LAST_CLIPBOARD_CONTENT = "last_clipboard_content";

    public static final String FLOAT_ICON = "float_icon";

    public static final String FLOAT_IS_RIGHT = "float_is_right";

    public static final String FLOAT_X = "float_x";

    public static final String FLOAT_Y = "float_y";

    private static SpManager instance;

    private SpManager() {
        // TODO 1. 有效性检测，资源清理 2. 数据初始化
    }

    public static SpManager getInstance() {
        if (null == instance) {
            synchronized (SpManager.class) {
                if (null == instance) {
                    instance = new SpManager();
                }
            }
        }

        return instance;
    }

    public String getGlobalString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public boolean setGlobalString(String key, String value) {
        return getEditor().putString(key, value).commit();
    }

    public boolean getGlobalBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public boolean setGlobalBoolean(String key, boolean value) {
        return getEditor().putBoolean(key, value).commit();
    }

    public SharedPreferences getPreferences() {
        return App.context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public int getGlobalInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public boolean setGlobalInt(String key, int value) {
        return getEditor().putInt(key, value).commit();
    }

}
