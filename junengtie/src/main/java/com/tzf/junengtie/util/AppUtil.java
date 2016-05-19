package com.tzf.junengtie.util;

import android.app.Service;
import android.content.ClipData;

import com.tzf.junengtie.App;

/**
 * Created by tangzhifei on 15/11/3.
 */
public class AppUtil {

    /**
     * 复制到剪切板
     * @param content
     */
    public static void copy(String content) {
        if (!StringUtil.isEmpty(content)) {
            if (android.os.Build.VERSION.SDK_INT > 11) {
                android.content.ClipboardManager newCm = (android.content.ClipboardManager) App.context.getSystemService(Service.CLIPBOARD_SERVICE);
                newCm.setPrimaryClip(ClipData.newPlainText("data", content));
            } else {
                android.text.ClipboardManager oldCm = (android.text.ClipboardManager) App.context.getSystemService(Service.CLIPBOARD_SERVICE);
                oldCm.setText(content);
            }
        }
    }

}
