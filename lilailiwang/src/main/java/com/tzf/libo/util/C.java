package com.tzf.libo.util;

public class C {

    public static final String BAIDU_CLIENT_ID = "OLR49XgxBW71VE2HxKB01tIY";

    public static final String BAIDU_RESPONSE_TYPE = "token";

    public static final String BAIDU_DISPLAY = "mobile";

    public static final String BAIDU_REDIRECT_URI = "http://weibo.com/baiduvsgoogle";

    public static final String BAIDU_AUTHORIZE_URL = "https://openapi.baidu.com/oauth/2.0/authorize?client_id=" + BAIDU_CLIENT_ID + "&redirect_uri=" + BAIDU_REDIRECT_URI + "&response_type=" + BAIDU_RESPONSE_TYPE + "&display=" + BAIDU_DISPLAY + "&scope=basic,netdisk";

    public static final String SP_FILE_NAME = "baidu";

    public static final String SP_KEY_ACCESS_TOKEN = "access_token";

    public static final String LOCAL_PASSWORD = "local_password";

    public static final int TYPE_DOWNLOAD = 1;

    public static final int TYPE_UPLOAD = 0;

}
