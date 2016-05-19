package com.mayigushi.common.util;

import android.app.Service;
import android.content.ClipData;
import android.content.Context;

/**
 * Created by tangzhifei on 15/11/3.
 */
public class AppUtil {

    /**
     * 复制到剪切板
     *
     * @param content
     */
    public static void copy(Context context, String content) {
        if (!StringUtil.isEmpty(content)) {
            if (android.os.Build.VERSION.SDK_INT > 11) {
                android.content.ClipboardManager newCm = (android.content.ClipboardManager) context.getSystemService(Service.CLIPBOARD_SERVICE);
                newCm.setPrimaryClip(ClipData.newPlainText("data", content));
            } else {
                android.text.ClipboardManager oldCm = (android.text.ClipboardManager) context.getSystemService(Service.CLIPBOARD_SERVICE);
                oldCm.setText(content);
            }
        }
    }

}
