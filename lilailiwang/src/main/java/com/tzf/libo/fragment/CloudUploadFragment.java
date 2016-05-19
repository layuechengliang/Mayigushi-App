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
import com.mayigushi.common.util.TimeUtil;
import com.tzf.libo.R;
import com.tzf.libo.util.C;
import com.tzf.libo.util.DBConstants;
import com.tzf.libo.util.LoginUtil;

/**
 * @author tangzhifei on 15/11/20.
 */
public class CloudUploadFragment extends Fragment implements View.OnClickListener {

    private Button cloundButton;

    private Handler uiThreadHandler;

    private Runnable upLoadRunnable = new Runnable() {

        @Override
        public void run() {
            BaiduPCSClient api = new BaiduPCSClient();
            api.setAccessToken(LoginUtil.getToken(getActivity()));
            api.rename("/apps/礼来礼往/libo.db", "libo-" + TimeUtil.getTime(System.currentTimeMillis(), "yyyy-MM-dd-HH-mm-ss") + ".db");
            api.uploadFile(DBConstants.DB_FILE_PATH, "/apps/礼来礼往/libo.db", new BaiduPCSStatusListener() {

                @Override
                public void onProgress(long bytes, long total) {
                    LogUtils.e(bytes);
                    int progress = (int) (bytes / total * 100);
                    final String text = bytes == total ? "备份完成" : progress + "%";
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

    public static CloudUploadFragment newInstance() {
        return new CloudUploadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_fragment, container, false);

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
            cloundButton.setText("正在备份…");
            new Thread(upLoadRunnable).start();
        }
    }

}