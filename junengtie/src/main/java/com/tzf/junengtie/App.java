package com.tzf.junengtie;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.tzf.junengtie.activity.HomeActivity;
import com.tzf.junengtie.util.SpManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tangzhifei on 15/11/1.
 */
public class App extends Application {

    public static Context context;

    private WindowManager mWindowManager;

    private LayoutParams mLayoutParams;

    private View view;

    private boolean isRight;

    private ImageView iv_anzai;

    private boolean isMove;

    private FloatIconOnClickListener mOnClickListener;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        // LogUtils 配置
        LogUtils.configAllowLog = BuildConfig.DEBUG;
        LogUtils.configTagPrefix = "Mayi-";

        // 初始化realm
        RealmConfiguration config = new RealmConfiguration.Builder(this).name("junengtie.realm").schemaVersion(1).build();
        Realm.setDefaultConfiguration(config);

        // 美洽初始化
        MQManager.init(context, "1008ee7388af67f59569738e41ba016f", new OnInitCallback() {

            @Override
            public void onSuccess(String clientId) {
                LogUtils.e("meiqia onSuccess.");
            }

            @Override
            public void onFailure(int code, String message) {
                LogUtils.e("meiqia onFailure.");
            }

        });

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    public void displayFloatImageView() {
        removedisplayFloatImageView();
        view = View.inflate(this, R.layout.float_icon, null);
        view.setOnClickListener(new FloatIconOnClickListener());
        mOnClickListener = new FloatIconOnClickListener();
        view.setOnTouchListener(new MyOnTouchListener());
        isRight = SpManager.getInstance().getGlobalBoolean(SpManager.FLOAT_IS_RIGHT, false);
        iv_anzai = (ImageView) view.findViewById(R.id.anzai_icon);
        if (isRight) {
            iv_anzai.setImageResource(R.drawable.ant_right_normal);
        } else {
            iv_anzai.setImageResource(R.drawable.ant_left_normal);
        }
        mLayoutParams = new LayoutParams();
        mLayoutParams.type = LayoutParams.TYPE_PHONE;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.x = SpManager.getInstance().getGlobalInt(SpManager.FLOAT_X, 0);
        mLayoutParams.y = SpManager.getInstance().getGlobalInt(SpManager.FLOAT_Y, mWindowManager.getDefaultDisplay().getHeight() / 2 - 60);
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowManager.addView(view, mLayoutParams);
    }

    public void removedisplayFloatImageView() {
        if (view != null) {
            mWindowManager.removeView(view);
        }
        view = null;
    }

    private class FloatIconOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Intent it = new Intent(getApplicationContext(), HomeActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
        }
    }

    private class MyOnTouchListener implements View.OnTouchListener {
        int startx;
        int starty;

        public boolean onTouch(View v, MotionEvent event) {
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 获取按下的时候相对于Activity左上角的坐标
                    startx = (int) event.getRawX();
                    starty = (int) event.getRawY();
                    if (isRight) {
                        iv_anzai.setImageResource(R.drawable.ant_right_pressed);
                    } else {
                        iv_anzai.setImageResource(R.drawable.ant_left_pressed);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int newx = (int) event.getRawX();
                    int newy = (int) event.getRawY();
                    if (newx > 35 && (screenWidth - newx) > 35) {
                        isMove = true;
                        iv_anzai.setImageResource(R.drawable.ant_middle_normal);
                        int dx = newx - startx;
                        int dy = newy - starty;
                        mLayoutParams.x += dx;
                        mLayoutParams.y += dy;
                        // 更新
                        mWindowManager.updateViewLayout(view, mLayoutParams);
                        // 对初始坐标重新赋值
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isMove) {
                        isMove = false;
                        float halfScreen = screenWidth / 2;
                        if (startx <= halfScreen) {
                            mLayoutParams.x = 0;
                            mWindowManager.updateViewLayout(view, mLayoutParams);
                            iv_anzai.setImageResource(R.drawable.ant_left_normal);
                            SpManager.getInstance().setGlobalInt(SpManager.FLOAT_X, 0);
                            isRight = false;
                        } else {
                            mLayoutParams.x = screenWidth;
                            mWindowManager.updateViewLayout(view, mLayoutParams);
                            iv_anzai.setImageResource(R.drawable.ant_right_normal);
                            SpManager.getInstance().setGlobalInt(SpManager.FLOAT_X, screenWidth);
                            isRight = true;
                        }
                        SpManager.getInstance().setGlobalInt(SpManager.FLOAT_Y, starty);
                        SpManager.getInstance().setGlobalBoolean(SpManager.FLOAT_IS_RIGHT, isRight);
                    } else {
                        if (isRight) {
                            iv_anzai.setImageResource(R.drawable.ant_right_normal);
                        } else {
                            iv_anzai.setImageResource(R.drawable.ant_left_normal);
                        }
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick(view);
                        }

                    }
                    break;
            }
            return true;
        }
    }

}
