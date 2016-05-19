package com.tzf.libo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mayigushi.common.util.SharedPreferencesUtil;
import com.mayigushi.common.util.StringUtil;

/**
 * @author tangzhifei on 15/12/13.
 */
public class LoginUtil {

    public static boolean needLogin(Context context) {
        return StringUtil.isEmpty(getToken(context));
    }

    public static String getToken(Context context) {
        SharedPreferences sp = SharedPreferencesUtil.getPreferences(context, C.SP_FILE_NAME);
        return sp.getString(C.SP_KEY_ACCESS_TOKEN, StringUtil.EMPTY_STRING);
    }

}
