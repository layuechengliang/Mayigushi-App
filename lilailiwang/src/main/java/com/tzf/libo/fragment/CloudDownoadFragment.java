package com.tzf.libo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.baidu.pcs.BaiduPCSClient;
import com.baidu.pcs.BaiduPCSStatusListener;
import com.tzf.libo.R;
import com.tzf.libo.util.C;
import com.tzf.libo.util.DBConstants;
import com.tzf.libo.util.LoginUtil;

/**
 * @author tangzhifei on 15/11/20.
 */
public class CloudDownoadFragment extends Fragment implements View.OnClickListener {

    private Button cloundButton;

    private Handler uiThreadHandler;

    private Runnable downLoadRunnable = new Runnable() {

        @Override
        public void run() {
            BaiduPCSClient api = new BaiduPCSClient();
            api.setAccessToken(LoginUtil.getToken(getActivity()));
            api.downloadFile("/apps/礼来礼往/libo.db", DBConstants.DB_FILE_PATH, new BaiduPCSStatusListener() {

                @Override
                public void onProgress(long bytes, long total) {
                    LogUtils.e("bytes:" + bytes);
                    LogUtils.e("total:" + total);
                    int progress = (int) (bytes / total * 100);
                    final String text = bytes == total ? "恢复完成" : progress + "%";
                    uiThreadHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            cloundButton.setText(text);
                        }

                    });
                }

                @Override
                public long progressInterval() {
                    return 1000;
                }

            });
        }

    };


    public static CloudDownoadFragment newInstance() {
        return new CloudDownoadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_fragment, container, false);

        if (LoginUtil.needLogin(getActivity())) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, BaiduLoginFragment.newInstance(C.TYPE_UPLOAD)).commit();
        }

        uiThreadHandler = new Handler();
        cloundButton = (Button) view.findViewById(R.id.cloundButton);
        cloundButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (R.id.cloundButton == v.getId()) {
            cloundButton.setClickable(false);
            cloundButton.setText("正在恢复…");
            new Thread(downLoadRunnable).start();
        }
    }

}