package com.tzf.libo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tzf.libo.R;
import com.tzf.libo.fragment.InputSettingFragment;

/**
 * @author tangzhifei on 16/1/10.
 */
public class InputSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_setting_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("设置输入项");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, InputSettingFragment.newInstance()).commit();
    }

}

