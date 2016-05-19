package com.tzf.libo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tzf.libo.R;
import com.tzf.libo.fragment.LoginFragment;

/**
 * @author tangzhifei on 16/1/10.
 */
public class LoginActivity extends BaseActivity {

    LoginFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("欢迎回来,请登录");

        fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.done == item.getItemId()) {
            fragment.login();
        }
        return super.onOptionsItemSelected(item);
    }

}
