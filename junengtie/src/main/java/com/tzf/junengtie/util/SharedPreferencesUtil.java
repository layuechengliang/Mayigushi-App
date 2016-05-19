package com.tzf.junengtie.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @ClassName      SharedPreferencesUtil.java
 * @Description    SharedPreferences工具类
 * @author         tangzhifei
 * @version        V1.0  
 * @Date           2015年6月3日 下午1:53:24
 */
public class SharedPreferencesUtil {
	
	public static String getString(Context context, String fileName, String key, String defValue) {
		return getPreferences(context, fileName).getString(key, defValue);
	}

	public static boolean setString(Context context, String fileName, String key, String value) {
		return getEditor(context, fileName).putString(key, value).commit();
	}
	
	public static int getInt(Context context, String fileName, String key, int defValue) {
		return getPreferences(context, fileName).getInt(key, defValue);
	}
	
	public static boolean setInt(Context context, String fileName, String key, int value) {
		return getEditor(context, fileName).putInt(key, value).commit();
	}
	
	public static boolean getBoolean(Context context, String fileName, String key, boolean defValue) {
		return getPreferences(context, fileName).getBoolean(key, defValue);
	}
	
	public static boolean setBoolean(Context context, String fileName, String key, boolean value) {
		return getEditor(context, fileName).putBoolean(key, value).commit();
	}
	
	public static SharedPreferences getPreferences(Context context, String fileName) {
		return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
	}
	
	public static Editor getEditor(Context context, String fileName) {
		return getPreferences(context, fileName).edit();
	}

}
