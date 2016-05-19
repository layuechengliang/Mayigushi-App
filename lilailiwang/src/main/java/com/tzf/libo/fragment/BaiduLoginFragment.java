package com.tzf.libo.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mayigushi.common.util.SharedPreferencesUtil;
import com.tzf.libo.R;
import com.tzf.libo.util.C;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * @author tangzhifei on 15/11/19.
 */
public class BaiduLoginFragment extends Fragment {

    private static final String CLOUND_TYPE = "clound_type";

    private int type;

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            String callback_url = C.BAIDU_REDIRECT_URI + "#";
            if (url.startsWith(callback_url)) {
                if (url.contains(C.SP_KEY_ACCESS_TOKEN)) {
                    Snackbar.make(webView, "登录成功", LENGTH_LONG).show();
                    int index = url.indexOf("#");
                    String[] array = url.substring(index + 1).split("&");
                    for (String str : array) {
                        if (str.startsWith(C.SP_KEY_ACCESS_TOKEN)) {
                            int d = str.indexOf("=");
                            SharedPreferences sp = SharedPreferencesUtil.getPreferences(getContext(), C.SP_FILE_NAME);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(C.SP_KEY_ACCESS_TOKEN, str.substring(d + 1));
                            editor.commit();

                            Fragment fragment = C.TYPE_DOWNLOAD == type ? CloudDownoadFragment.newInstance() : CloudUploadFragment.newInstance();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragment).commit();
                        }
                    }
                } else {
                    Snackbar.make(webView, "登录失败", LENGTH_LONG).show();
                }
            } else {
                super.onPageStarted(webView, url, favicon);
            }
        }

    };

    public static BaiduLoginFragment newInstance(int cloundType) {
        BaiduLoginFragment fragment = new BaiduLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CLOUND_TYPE, cloundType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            type = bundle.getInt(CLOUND_TYPE);
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baidu_login_fragment, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setSavePassword(false);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(C.BAIDU_AUTHORIZE_URL);
        return view;
    }

}
