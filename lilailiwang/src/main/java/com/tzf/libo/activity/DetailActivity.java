package com.tzf.libo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mayigushi.common.util.StringUtil;
import com.tzf.libo.R;
import com.tzf.libo.fragment.InputFragment;

/**
 * @author tangzhifei on 15/11/22.
 */
public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String from = intent.getStringExtra("from");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        if (!StringUtil.isEmpty(from)) {
            Fragment fragment = null;

            switch (from) {
                case "input":
                    fragment = InputFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", intent.getStringExtra("title"));
                    bundle.putInt("input_type", intent.getIntExtra("input_type", -1));
                    fragment.setArguments(bundle);
                    break;
            }

            if (null != fragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragment).commit();
            }
        }
    }

}
