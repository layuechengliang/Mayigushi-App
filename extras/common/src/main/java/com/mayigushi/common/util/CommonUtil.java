package com.mayigushi.common.util;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class CommonUtil {

	public static void sendMsgToWeixin(Context context,IWXAPI api, int scene, String content) {
		if (null == context || null == api || StringUtil.isEmpty(content)) {
			return;
		}

		WXTextObject textObj = new WXTextObject();
		textObj.text = content;

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = textObj.text;

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text");
		req.message = msg;
		req.scene = scene;
		api.sendReq(req);
	}
	
	private static String buildTransaction(String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

}
