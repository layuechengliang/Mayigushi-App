package com.tzf.libo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.tzf.libo.R;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.fragment.IncomeDetailFragment;
import com.tzf.libo.util.DBConstants;

/**
 * @author tangzhifei on 15/11/29.
 */
public class IncomeDetailActivity extends BaseActivity {

    private Toolbar toolbar;

    private int id;

    private int subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        subject = intent.getIntExtra("subject", 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(DataManager.getBaseKey(subject, DBConstants.COMMON_ITEM_SUBJECT));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        Fragment fragment = IncomeDetailFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.income_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.delete:
                new AlertDialogWrapper.Builder(this)
                        .setTitle("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBTableManager.getInstance(IncomeDetailActivity.this).deleteIncome(id);
                                Snackbar.make(toolbar, "删除成功", Snackbar.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
            case R.id.edit:
                Intent intent = new Intent(this, IncomeEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
