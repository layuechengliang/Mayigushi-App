package com.tzf.junengtie.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mayigushi.common.fragment.RecommendFragment;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.tzf.junengtie.App;
import com.tzf.junengtie.R;
import com.tzf.junengtie.fragment.AboutFragment;
import com.tzf.junengtie.fragment.HomeFragment;
import com.tzf.junengtie.fragment.SettingFragment;
import com.tzf.junengtie.service.ClipboardService;
import com.tzf.junengtie.service.FloatService;
import com.umeng.update.UmengUpdateAgent;

import java.util.List;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // umeng自动更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, HomeFragment.newInstance()).commit();

        if (!isServiceRunning(getApplicationContext(), ClipboardService.class.getName())) {
            Intent it = new Intent();
            it.setPackage(getPackageName());
            it.setAction("com.tzf.junengtie.ClipboardService");
            startService(it);
        }

        if (!isServiceRunning(getApplicationContext(), FloatService.class.getName())) {
            Intent it = new Intent(this, FloatService.class);
            startService(it);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((App) getApplication()).removedisplayFloatImageView();
    }

    @Override
    protected void onDestroy() {
        sendBroadcast(new Intent(FloatService.ISHOME));
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, HomeFragment.newInstance()).commit();
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, AboutFragment.newInstance()).commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, SettingFragment.newInstance()).commit();
                break;
            case R.id.recommend:
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, RecommendFragment.newInstance()).commit();
                break;
            case R.id.service:
                Intent it = new Intent(HomeActivity.this, MQConversationActivity.class);
                startActivity(it);
                break;
        }

        return true;
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(50);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}
