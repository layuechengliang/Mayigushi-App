package com.tzf.libo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mayigushi.common.fragment.RecommendFragment;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.tzf.libo.R;
import com.tzf.libo.fragment.AboutFragment;
import com.tzf.libo.fragment.CloudDownoadFragment;
import com.tzf.libo.fragment.CloudUploadFragment;
import com.tzf.libo.fragment.HomeFragment;
import com.tzf.libo.fragment.SettingFragment;
import com.tzf.libo.wxapi.WXEntryActivity;
import com.umeng.update.UmengUpdateAgent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tangzhifei on 15/11/15.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isExit = false;

    private MenuItem addMenuItem;

    private MenuItem searchMenuItem;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // umeng自动更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        homeFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, homeFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            Snackbar.make(drawerLayout, "再按一次退出", Snackbar.LENGTH_LONG).show();
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    isExit = false;
                }

            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        addMenuItem = menu.findItem(R.id.add);
        searchMenuItem = menu.findItem(R.id.search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                homeFragment.add();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.home:
                addMenuItem.setVisible(true);
                searchMenuItem.setVisible(true);
                homeFragment = HomeFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, homeFragment).commit();
                break;
            case R.id.settings:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, SettingFragment.newInstance()).commit();
                break;
            case R.id.service:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                Intent it = new Intent(HomeActivity.this, MQConversationActivity.class);
                startActivity(it);
                break;
            case R.id.upload:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, CloudUploadFragment.newInstance()).commit();
                break;
            case R.id.download:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, CloudDownoadFragment.newInstance()).commit();
                break;
            case R.id.about:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, AboutFragment.newInstance()).commit();
                break;
            case R.id.recommend:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, RecommendFragment.newInstance()).commit();
                break;
            /*case R.id.chart:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                Snackbar.make(drawerLayout, "下版本开发,敬请期待...", Snackbar.LENGTH_LONG).show();
                break;*/
            case R.id.share:
                addMenuItem.setVisible(false);
                searchMenuItem.setVisible(false);
                Intent intent = new Intent(this, WXEntryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", "我正在使用礼来礼往记录随礼,云储存,安全便捷,您也下载个试试吧:http://fir.im/27rg");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

        return true;
    }

}