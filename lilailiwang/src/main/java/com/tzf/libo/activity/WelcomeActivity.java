package com.tzf.libo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.mayigushi.common.util.SharedPreferencesUtil;
import com.mayigushi.common.util.StringUtil;
import com.tzf.libo.R;
import com.tzf.libo.util.C;

/**
 * @author tangzhifei on 16/1/10.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent();
                SharedPreferences sp = SharedPreferencesUtil.getPreferences(WelcomeActivity.this, C.SP_FILE_NAME);
                String password = sp.getString(C.LOCAL_PASSWORD, StringUtil.EMPTY_STRING);
                if (StringUtil.isEmpty(password)) {
                    intent.setClass(WelcomeActivity.this, HomeActivity.class);
                } else {
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }

        }, 2000L);
    }

}
